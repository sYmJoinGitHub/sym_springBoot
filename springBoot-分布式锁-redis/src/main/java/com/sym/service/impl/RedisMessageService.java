package com.sym.service.impl;

import com.sym.service.IRedisMessageService;
import com.sym.util.LockSupportUtil;

/**
 * @Auther: shenym
 * @Date: 2019-04-02 17:08
 */
public class RedisMessageService implements IRedisMessageService {


    /**
     * 处理redis分布式锁的方法
     *
     * @param message 锁的Key
     */
    @Override
    public void lockHandler(String message) {
        // message规定为分布式锁的key
        LockSupportUtil.unPark(message);
    }
}
