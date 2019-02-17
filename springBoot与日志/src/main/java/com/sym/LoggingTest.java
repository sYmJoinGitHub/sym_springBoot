package com.sym;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springBoot中使用日志
 *
 * Created by 沈燕明 on 2018/9/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoggingTest.class)
public class LoggingTest {

    // 定义日志记录器
    private static Logger LOGGER = LoggerFactory.getLogger(LoggingTest.class);

    /**
     * 直接运行此方法，观察springBoot的日志打印情况
     */
    @Test
    public void context(){
        // 记录不同级别的日志
        LOGGER.trace("trace级别...");
        LOGGER.debug("debug级别...");
        LOGGER.info("info级别...");
        LOGGER.warn("wrn级别...");
        LOGGER.error("error级别...");
    }
}
