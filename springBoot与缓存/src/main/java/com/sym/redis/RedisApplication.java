package com.sym.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 整合redis的启动类
 *
 * Created by 沈燕明 on 2018/11/22.
 */
@SpringBootApplication
@EnableCaching //开启缓存注解
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }
}
