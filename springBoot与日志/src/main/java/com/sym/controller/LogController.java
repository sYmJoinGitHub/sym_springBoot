package com.sym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 沈燕明 on 2019/5/14 16:49.
 */
@RestController
@PropertySource(value = "classpath:myTest.properties")
public class LogController {

    // 定义日志记录器
    private static Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private Environment env;

    @RequestMapping("prop")
    public String getProp(String key) throws Exception{
        return env.getProperty(key)==null?"获取不到":env.getProperty(key);
    }

    @GetMapping("log")
    public void printLog() throws Exception{
        // 记录不同级别的日志
        LOGGER.trace("trace级别...");
        LOGGER.debug("debug级别...");
        LOGGER.info("info级别...");
        LOGGER.warn("wrn级别...");
        LOGGER.error("error级别...");
    }
}
