package com.sym.service;

/**
 * 自己实现的分布式锁接口
 *
 * @Auther: shenym
 * @Date: 2019-03-22 16:04
 */
public interface IRedisLock {

    /**
     * 加锁，不阻塞
     * @return true表示加锁成功，false表示加锁失败
     */
    boolean lock();

    /**
     * 加锁，不阻塞
     * @param ttlTime key存活时间
     * @return true表示加锁成功，false表示加锁失败
     */
    boolean lock(int ttlTime);

    /**
     * 加锁，并且未获取到锁时阻塞
     * @return true表示加锁成功，false表示加锁失败
     */
    boolean lockWithBlock();

    /**
     * 加锁，并且未获取到锁时阻塞
     * @param ttlTime key存活时间
     * @return true表示加锁成功，false表示加锁失败
     */
    boolean lockWithBlock(int ttlTime);

    /**
     * 解锁
     * @return true-解锁，false-解锁失败
     */
    boolean unLock();
}
