package com.sym.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * springBoot与dubbo集成的启动类, 作为服务提供方, 通过
 * * {@link EnableDubbo}开启springBoot的dubbo功能
 *
 * @author shenym
 * @date 2020/3/15 23:10
 */
@SpringBootApplication
@EnableDubbo
@Slf4j
@Profile("provider")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
        log.info("提供者服务启动成功~");
    }
}
