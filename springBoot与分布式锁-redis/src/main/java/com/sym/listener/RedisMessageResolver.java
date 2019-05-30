package com.sym.listener;

import com.sym.util.LockSupportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理加锁Key被释放后 / key过期 。 redis发出的消息
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
        logger.info("从通道中监听到的消息={}",message);
        LockSupportUtil.unPark(message);
    }
}
