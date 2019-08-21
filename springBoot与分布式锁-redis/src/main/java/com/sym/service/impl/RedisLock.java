package com.sym.service.impl;

import com.sym.service.IRedisLock;
import com.sym.util.LockSupportUtil;
import com.sym.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 自己实现的分布式锁,利用redis执行lua脚本是原子性的特点,使用lua脚本来完成加锁逻辑与解锁逻辑
 *
 * 加锁脚本解释：
 *      1.使用exists判断key是否存在,如果不存在,执行hmset+expire命令,表示获取到锁,lua脚本返回1(表示加锁成功)
 *      2.如果key已经存在,使用hget命令判断hash值里面的threadId是否是当前申请加锁的线程唯一标识,如果不是说明此时锁被另一个线程占用,lua脚本返回0(表示加锁失败)
 *      3.如果key已经存在,并且hash值里面的threadId是当前申请加锁的线程,则将hash值里面的count累加1,表示锁重入次数+1,重新设置过期时间,lua脚本返回1(表示加锁成功)
 * 解锁脚本解释：
 *      1.使用exists判断key是否存在,如果不存在,说明没有线程在占用锁,也就没必要解锁,lua脚本返回0(表示解锁失败)
 *      2.如果key已经存在,使用hget命令判断hash值里面的threadId是否是当前申请解锁的线程,如果不是说明当前锁不是被该线程占用,它就没资格解锁,lua脚本返回0(表示解锁失败)
 *      3.如果hash值内的threadId是当前申请解锁的线程,将hash值内的count减1,若count不等于0,说明解锁次数仍小于加锁次数,lua脚本返回0(表示解锁失败)
 *      4.若count值减1后等于0,说明解锁次数==加锁次数,删除key,并使用publish命令发布一条删除key的消息
 *
 * @Auther: shenym
 * @Date: 2019-03-25 9:56
 */
public class RedisLock implements IRedisLock {

    /**
     * 加锁脚本(lua)
     *
     * 外层键 -- KEYS[1]
     * 线程ID -- KEYS[2]  ARGV[1]
     * 加锁次数 -- KEYS[3]  ARGV[2]
     * 超时时间(秒) -- ARGV[3]
     */
    private static String lockScript =
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
     *
     * 外层键 -- KEYS[1]
     * 线程ID -- KEYS[2]  ARGV[1]
     * 加锁次数 -- KEYS[3]
     */
    private static String unLockScript =
            "if(redis.call('exists',KEYS[1]) == 1) then " +
            "   if(redis.call('hget',KEYS[1],KEYS[2]) == ARGV[1]) then " +
            "       local count = redis.call('hincrby',KEYS[1],KEYS[3],-1) " +
            "       if(count==0) then " +
            "           redis.call('del',KEYS[1]) " +
            "           redis.call('publish','redis-lock',KEYS[1]) " +
            "           return 1 " +
            "       else return 0 end " +
            "   else return 0 end " +
            "else return 0 end";


    /* 加锁脚本的缓存SHA */
    private static String lockScript_SHA;

    /* 解锁脚本的缓存SHA */
    private static String unLockScript_SHA;

    /* 表示此实例的加锁key */
    private String lockKey;

    /* 表示此实例的线程ID */
    private String threadId;

    private static RedisTemplate<String,String> redisTemplate;

    private static Charset utf8 = Charset.forName("utf-8");

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    static{
        init();
    }

    private static void init(){
        // 获取RedisTemplate实例
        redisTemplate = (RedisTemplate)SpringContextUtil.getBean("redisTemplate");
        // 先去redis服务器缓存脚本
        redisTemplate.execute((RedisCallback<String>)conn->{
            lockScript_SHA = conn.scriptLoad(lockScript.getBytes());
            unLockScript_SHA = conn.scriptLoad(unLockScript.getBytes());
            LOGGER.info("缓存加锁脚本：{}",lockScript_SHA);
            LOGGER.info("缓存解锁脚本：{}",unLockScript_SHA);
            return null;
        });
    }

    private RedisLock(){

    }

    public RedisLock(String lockKey){
        Assert.hasLength(lockKey,"key must not be null");
        this.lockKey = lockKey;
        this.threadId = getThreadId();
    }

    private String getThreadId(){
        return UUID.randomUUID().toString().replace("-","").concat(System.currentTimeMillis()+"");
    }


    /**
     * 加锁 默认存活时间60s
     * @return
     */
    @Override
    public boolean lock() {
        return lock(60);
    }

    /**
     * 加锁
     *
     * @param ttlTime key存活时间
     * @return true表示加锁成功，false表示加锁失败
     */
    @Override
    public boolean lock(int ttlTime) {
        return tryLock(ttlTime);
    }

    /**
     * 加锁，并且未获取到锁时阻塞
     *
     * @return true表示加锁成功，false表示加锁失败
     */
    @Override
    public boolean lockWithBlock() {
        return lockWithBlock(60);
    }

    /**
     * 加锁，并且未获取到锁时阻塞
     *
     * @param ttlTime key存活时间
     * @return true表示加锁成功，false表示加锁失败
     */
    @Override
    public boolean lockWithBlock(int ttlTime) {
        for(;;){
            if( lock(ttlTime) )
                return true;
            else {
                try {
                    // 线程在此阻塞
                    LockSupportUtil.park(lockKey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    /**
     * 解锁
     * @return
     */
    @Override
    public boolean unLock() {
        return release();
    }

    /**
     * 实际加锁方法 Distributed
     * @param ttlTime
     * @return
     */
    private boolean tryLock(int ttlTime){
        Boolean result = false;
        byte[] lockKeyBytes = lockKey.getBytes(utf8);
        byte[] uuidBytes = "uuid".getBytes(utf8);
        byte[] countBytes = "count".getBytes(utf8);
        byte[] threadIdBytes = threadId.getBytes(utf8);
        byte[] bytes = "1".getBytes(utf8);
        byte[] ttlTimeBytes = Integer.toString(ttlTime).getBytes(utf8);
        try {
            result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                // 尝试加锁
                Long o = conn.evalSha(lockScript_SHA, ReturnType.INTEGER, 3, lockKeyBytes, uuidBytes,
                        countBytes, threadIdBytes, bytes, ttlTimeBytes);
                if(o == 1L) {
                    LOGGER.info("thead {},获取到锁-{}",Thread.currentThread().getName(),lockKey);
                    return true;
                }
                return false;
            });
        }catch (Exception e){
            Throwable cause = e.getCause();
            if( cause instanceof JedisNoScriptException){
                //由于redis的脚本缓存被清空了,直接使用脚本执行
                result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                    Long evalResult = conn.eval(lockScript.getBytes(utf8), ReturnType.INTEGER, 3, lockKeyBytes, uuidBytes,
                            countBytes, threadIdBytes, bytes, ttlTimeBytes);
                    if(evalResult == 1L) {
                        LOGGER.info("thead {},获取到锁-{}",Thread.currentThread().getName(),lockKey);
                        return true;
                    }
                    return false;
                });
            }else {
                LOGGER.error("加锁异常，原因：{}",e.getMessage());
            }
        }
        // 有可能执行完 redisTemplate.execute()后返回null
        return result == null?false:result;
    }


    /**
     * 实际解锁逻辑
     * @return
     */
    private boolean release(){
        Boolean result = false;
        byte[] lockKeyBytes = lockKey.getBytes(utf8);
        byte[] uuidBytes = "uuid".getBytes(utf8);
        byte[] countBytes = "count".getBytes(utf8);
        byte[] threadIdBytes = threadId.getBytes(utf8);
        try {
            result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                // 尝试解锁
                Long o = conn.evalSha(unLockScript_SHA,ReturnType.INTEGER,3,lockKeyBytes, uuidBytes,
                        countBytes,threadIdBytes);
                return o == 1L;
            });
        }catch (Exception e){
            Throwable cause = e.getCause();
            if( cause instanceof JedisNoScriptException){
                //由于redis的脚本缓存被清空了,直接使用脚本执行
                result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                    Long evalResult = conn.eval(unLockScript.getBytes(utf8), ReturnType.INTEGER, 3, lockKeyBytes, uuidBytes,
                            countBytes, threadIdBytes);
                    return evalResult == 1L;
                });
            }else {
                LOGGER.error("解锁异常，原因：{}",e.getMessage());
            }
        }
        return result == null?false:result;
    }
}
