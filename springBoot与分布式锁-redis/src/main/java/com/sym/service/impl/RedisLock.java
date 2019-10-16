package com.sym.service.impl;

import com.sym.service.AbstractMapSynchronizer;
import com.sym.service.RedisOperations;
import com.sym.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自己实现的分布式锁,利用redis执行lua脚本是原子性的特点,使用lua脚本来完成加锁逻辑与解锁逻辑
 * <p>
 * <b>加锁脚本解释：</b>
 *      1.使用exists判断key是否存在,如果不存在,执行hmset+expire命令,表示获取到锁,lua脚本返回1(表示加锁成功)
 *      2.如果key已经存在,使用hget命令判断hash值里面的threadId是否是当前申请加锁的线程唯一标识,如果不是说明此时锁被另一个线程占用,lua脚本返回0(表示加锁失败)
 *      3.如果key已经存在,并且hash值里面的threadId是当前申请加锁的线程,则将hash值里面的count累加1,表示锁重入次数+1,重新设置过期时间,lua脚本返回1(表示加锁成功)
 * 解锁脚本解释：
 *      1.使用exists判断key是否存在,如果不存在,说明没有线程在占用锁,也就没必要解锁,lua脚本返回0(表示解锁失败)
 *      2.如果key已经存在,使用hget命令判断hash值里面的threadId是否是当前申请解锁的线程,如果不是说明当前锁不是被该线程占用,它就没资格解锁,lua脚本返回0(表示解锁失败)
 *      3.如果hash值内的threadId是当前申请解锁的线程,将hash值内的count减1,若count不等于0,说明解锁次数仍小于加锁次数,lua脚本返回0(表示解锁失败)
 *      4.若count值减1后等于0,说明解锁次数==加锁次数,删除key,并使用publish命令发布一条删除key的消息
 * <p>
 * Created by shenym on 2019/3/25.
 */
public class RedisLock extends AbstractMapSynchronizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    /**
     * 加锁脚本(lua)
     * <p>
     * 外层键 -- KEYS[1]
     * 线程ID -- KEYS[2]  ARGV[1]
     * 加锁次数 -- KEYS[3]  ARGV[2]
     * 超时时间(秒) -- ARGV[3]
     */
    private final static String LOCK_SCRIPT =
            "if(redis.call('exists',KEYS[1]) == 1) then " +
                    "  if(redis.call('hget',KEYS[1],KEYS[2]) == ARGV[1]) then " +
                    "    redis.call('hincrby',KEYS[1],KEYS[3],1) " +
                    "    redis.call('expire',KEYS[1],ARGV[3]) " +
                    "    return 1 " +
                    "  else return 0 end " +
                    "else " +
                    "  redis.call('hmset',KEYS[1],KEYS[2],ARGV[1],KEYS[3],ARGV[2])" +
                    "  redis.call('expire',KEYS[1],ARGV[3])" +
                    "  return 1 " +
                    "end";


    /**
     * 解锁脚本(lua)
     * <p>
     * 外层键 -- KEYS[1]
     * 线程ID -- KEYS[2]  ARGV[1]
     * 加锁次数 -- KEYS[3]
     */
    private final static String UNLOCK_SCRIPT =
            "if(redis.call('exists',KEYS[1]) == 1) then " +
                    "   if(redis.call('hget',KEYS[1],KEYS[2]) == ARGV[1]) then " +
                    "       local count = redis.call('hincrby',KEYS[1],KEYS[3],-1) " +
                    "       if(count==0) then " +
                    "           redis.call('del',KEYS[1]) " +
                    "           redis.call('publish','_$redis_$lock',KEYS[1]) " +
                    "           return 1 " +
                    "       else return 0 end " +
                    "   else return 0 end " +
                    "else return 0 end";


    /* 加锁脚本的缓存SHA */
    private static String lockScript_sha;

    /* 解锁脚本的缓存SHA */
    private static String unlockScript_sha;

    /* 标识：是否已经进行脚本缓存初始化 */
    private static volatile boolean isInit = false;

    /* 表示此实例的线程ID */
    private String threadId;

    public RedisLock(String lockKey){
        this(lockKey, new DefaultRedisOperations(SpringContextUtil.getBean(StringRedisTemplate.class)));
    }

    public RedisLock(String lockKey,RedisOperations redisOperations) {
        super(lockKey, redisOperations);
        this.threadId = getThreadId();
        if( !isInit ){
            // 如果未初始化
            synchronized (RedisLock.class){
                if( !isInit ) {
                    this.cacheScript();
                    isInit = true;
                }
            }
        }
    }


    /**
     * 生成当前线程的唯一标识符
     * @return
     */
    private String getThreadId() {
        String threadId = UUID.randomUUID().toString().replace("-", "");
        LOGGER.info("生成的线程标识符：{}", threadId);
        return threadId;
    }

    /**
     * 缓存脚本
     */
    private void cacheScript(){
        lockScript_sha = this.redisOperations.loadScript(LOCK_SCRIPT);
        unlockScript_sha = this.redisOperations.loadScript(UNLOCK_SCRIPT);
    }


    /**
     * 尝试一次获取锁资源, 方法立即返回
     * @param lockTime 获取到锁时, 对锁的占用时间
     * @return
     */
    @Override
    protected boolean tryRequire(int lockTime) {
        boolean result;
        try {
            result =  doLock(lockTime);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof JedisNoScriptException) {
                //由于redis的脚本缓存被清空了,重新缓存脚本
                cacheScript();
                //重新申请锁
                result = doLock(lockTime);
            } else {
                LOGGER.error("加锁异常，原因：{}", e.getMessage());
                throw e;
            }
        }
        return result;
    }


    /**
     * 尝试一次解锁, 方法立即返回
     * @return
     */
    @Override
    protected boolean tryRelease() {
        boolean result = false;
        try {
            result = this.doUnlock();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof JedisNoScriptException) {
                //由于redis的脚本缓存被清空了,重新缓存脚本
                cacheScript();
                //重新申请锁
                result = this.doUnlock();
            } else {
                LOGGER.error("解锁异常，原因：{}", e.getMessage());
            }
        }
        return result;
    }


    /**
     * 实际加锁逻辑
     * @param ttlTime 加锁时间
     * @return true-获得锁,false-未获取到锁
     */
    private boolean doLock(int ttlTime){
        // 执行脚本
        Boolean result = redisOperations.evalSha(lockScript_sha,3,lockKey, "uuid", "count", threadId, "1", Integer.toString(ttlTime));
        boolean f = result == null ? false : result;
        if( f ) LOGGER.info("线程[{}]获取到锁[{}]",Thread.currentThread().getName(),lockKey);
        return f;
    }


    /**
     * 实际解锁逻辑
     * @return true-解锁成功,false-解锁失败
     */
    private boolean doUnlock(){
        // 执行脚本
        Boolean result = redisOperations.evalSha(unlockScript_sha,3,lockKey, "uuid", "count", threadId);
        boolean f = result == null ? false : result;
        if( f ) LOGGER.info("线程[{}]已成功解锁[{}]",Thread.currentThread().getName(),lockKey);
        return f;
    }

}
