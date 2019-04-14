package com.sym;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: shenym
 * @Date: 2019-03-08 9:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void testOne(){
        LOGGER.debug("搭建ELK平台...");
        LOGGER.info("君不见黄河之水天上来");
        LOGGER.warn("奔流到海不复回");
        LOGGER.error("君不见高堂明镜悲白发");
        LOGGER.trace("朝如青丝暮成雪");
    }

}
