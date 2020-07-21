package com.sym.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * springBoot与dubbo集成的启动类，作为服务消费方, 通过
 * {@link EnableDubbo}开启springBoot的dubbo功能
 *
 * @author shenyanming
 * @date 2018/12/2
 */
@SpringBootApplication
@EnableDubbo
@Slf4j
@Profile("consumer")
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        log.info("消费者服务启动成功~");
    }
}
