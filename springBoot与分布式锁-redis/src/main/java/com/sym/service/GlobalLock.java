package com.sym.service;

/**
 * 分布式锁接口
 *
 * Created by shenym on 2019/9/18.
 */
public interface GlobalLock {


    /**
     * 尝试加锁一次, 方法会立即返回
     * @param lockTime 加锁时间(单位：秒)
     * @return true表示加锁成功，false表示加锁失败
     */
    boolean lock(Integer lockTime);


    /**
     * 尝试加锁，并且未获取到锁时阻塞
     * @param lockTime 加锁时间(单位：秒)
     * @return
     */
    boolean lockAwait(Integer lockTime) throws InterruptedException;


    /**
     * 尝试加锁，并且未获取到锁时阻塞
     * @param lockTime 加锁时间(单位：秒)
     * @param waitTime 阻塞等待时间(单位：秒)
     * @return
     */
    boolean lockAwait(Integer lockTime,Integer waitTime) throws InterruptedException;


    /**
     * 尝试解锁
     * @return true-解锁，false-解锁失败
     */
    boolean unlock();


    /**
     * 暴力解锁，此方法可能导致异常，慎用
     */
    void forceUnlock();
}
