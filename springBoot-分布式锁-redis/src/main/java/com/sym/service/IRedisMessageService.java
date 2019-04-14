package com.sym.service;

/**
 * @Auther: shenym
 * @Date: 2019-04-02 17:06
 */
public interface IRedisMessageService {

    /**
     * 处理redis分布式锁的方法
     * @param message
     */
    void lockHandler(String message);
}
