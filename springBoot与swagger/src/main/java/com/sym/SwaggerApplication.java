package com.sym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * swagger启动类. 启动成功后访问：http://127.0.0.1:8080/swagger-ui.html
 *
 * @author shenyanming
 * Created on 2020/6/9 14:11
 */
@SpringBootApplication
@Slf4j
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
        log.info("服务启动成功");
    }
}
