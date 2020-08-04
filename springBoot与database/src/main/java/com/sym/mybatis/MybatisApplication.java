package com.sym.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author shenyanming
 * @date 2020/8/4 22:14.
 */
@Slf4j
@SpringBootApplication
@MapperScan(value = "com.sym.mybatis.anon")
@ActiveProfiles("mybatis")
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
        log.info("服务启动~");
    }
}

