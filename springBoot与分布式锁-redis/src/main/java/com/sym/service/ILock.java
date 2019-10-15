package com.sym.service;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * Created by shenym on 2019/9/18.
 */
public interface ILock {

    /**
     * 尝试申请锁一次, 此方法会立即返回
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：秒), 必须指定否则容易造成死锁
     * @return true-成功, false-失败
     */
    boolean lock(int lockTime);


    /**
     * 尝试申请锁, 并且在未获取到锁时阻塞; 方法会一直阻塞, 直到被唤醒
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：秒), 必须指定否则容易造成死锁
     */
    void lockAwait(int lockTime) throws InterruptedException;


    /**
     * 尝试申请锁, 并且未获取到锁时阻塞; 方法会阻塞一段时间, 到点了如果还没有获取到锁便返回
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：毫秒), 必须指定否则容易造成死锁
     * @param waitTime 阻塞等待时间
     * @param timeUnit 阻塞等待时间的单位
     */
    void lockAwait(int lockTime, long waitTime, TimeUnit timeUnit) throws InterruptedException;


    /**
     * 尝试解锁
     * @return true-解锁，false-解锁失败
     */
    boolean unlock();
}
