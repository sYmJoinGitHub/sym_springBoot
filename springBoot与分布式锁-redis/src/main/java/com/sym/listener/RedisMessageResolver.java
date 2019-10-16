package com.sym.listener;

import com.sym.service.AbstractMapSynchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * springBoot 1.x 当监听到redis通道内的消息时, 就会调用此方法来处理
 * @see RedisMessageListener
 *
 * Created by 沈燕明 on 2019/5/29 17:47.
 */
public class RedisMessageResolver {

    private final static Logger logger = LoggerFactory.getLogger(RedisMessageResolver.class);

    /**
     * 处理监听到的消息
     * @param message 表示加锁的Key
     */
    public void handlerMessage(String message){
        logger.debug("从通道中监听到的消息={}",message);
        AbstractMapSynchronizer.unpark(message);
    }
}
