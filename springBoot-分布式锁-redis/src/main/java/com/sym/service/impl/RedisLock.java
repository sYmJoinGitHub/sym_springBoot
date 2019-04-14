package com.sym.service.impl;

import com.sym.service.IRedisLock;
import com.sym.util.LockSupportUtil;
import com.sym.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.locks.LockSupport;

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
        redisTemplate = SpringUtil.getBean(RedisTemplate.class);
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
        if( StringUtils.isEmpty( lockKey )){
            throw new NullPointerException("key must not be null");
        }
        this.lockKey = lockKey;
        this.threadId = UUID.randomUUID().toString().replace("-","");
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
                // 线程在此阻塞
                try {
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
     * 实际加锁方法
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
                    LOGGER.info("线程-{},获取到锁-{}",Thread.currentThread().getName(),lockKey);
                    return true;
                }
                else return false;
            });
        }catch (Exception e){
            LOGGER.error("加锁异常，原因：{}",e.getMessage());
        }
        if( result == null ) return false;
        return result;
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
                if(o == 1L) return true;
                else return false;
            });

        }catch (Exception e){
            LOGGER.error("解锁异常，原因：{}",e.getMessage());
        }
        return result;
    }
}
