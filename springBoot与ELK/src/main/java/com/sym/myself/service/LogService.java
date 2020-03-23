package com.sym.myself.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Auther: shenym
 * @Date: 2019-03-08 9:48
 */
@Service
public class LogService {

    // 定义日志记录器
    private static Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    /**
     * 打印各种日志
     */
    public void printLog(){
        // 记录不同级别的日志
        LOGGER.trace("trace级别...");
        LOGGER.debug("debug级别...");
        LOGGER.info("info级别...");
        LOGGER.warn("wrn级别...");
        LOGGER.error("error级别...");
    }
}
