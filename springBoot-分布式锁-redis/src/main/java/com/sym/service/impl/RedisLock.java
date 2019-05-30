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

import java.util.UUID;

/**
 * 自己实现的分布式锁
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
        try {
            result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                // 尝试加锁
                Long o = conn.evalSha(lockScript_SHA, ReturnType.INTEGER, 3, lockKey.getBytes(), "uuid".getBytes(),
                        "count".getBytes(), threadId.getBytes(), "1".getBytes(), String.valueOf(ttlTime).getBytes());
                if(o == 1L) {
                    LOGGER.info("thead {},获取到锁-{}",Thread.currentThread().getName(),lockKey);
                    return true;
                }
                return false;
            });
        }catch (Exception e){
            LOGGER.error("加锁异常，原因：{}",e.getMessage());
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
        try {
            result = redisTemplate.execute((RedisCallback<Boolean>)conn->{
                // 尝试解锁
                Long o = conn.evalSha(unLockScript_SHA,ReturnType.INTEGER,3,lockKey.getBytes(), "uuid".getBytes(),
                        "count".getBytes(),threadId.getBytes());
                return o == 1L;
            });
        }catch (Exception e){
            LOGGER.error("解锁异常，原因：{}",e.getMessage());
        }
        return result == null?false:result;
    }
}
