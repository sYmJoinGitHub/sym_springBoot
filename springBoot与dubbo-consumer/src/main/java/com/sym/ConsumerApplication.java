package com.sym;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springBoot与dubbo集成的启动类，作为服务消费方
 *
 * Created by 沈燕明 on 2018/12/2.
 */
@SpringBootApplication
@EnableDubbo // 开启springBoot的dubbo功能
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
