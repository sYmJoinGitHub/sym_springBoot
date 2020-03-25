package com.sym;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springBoot与dubbo集成的启动类, 作为服务提供方
 *
 * @author shenym
 * @date 2020/3/15 23:10
 */
@SpringBootApplication
@EnableDubbo //启动dubbo的自动化配置
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
