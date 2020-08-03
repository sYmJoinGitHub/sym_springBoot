package com.sym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenyanming
 * @date 2020/8/3 21:45.
 */
@SpringBootApplication
@Slf4j
public class MvcApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
        log.info("服务启动完成~");
    }
    
}
