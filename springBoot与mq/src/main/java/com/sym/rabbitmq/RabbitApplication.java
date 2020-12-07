package com.sym.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rabbitMQ的启动类
 * <p>
 * Created by 沈燕明 on 2018/11/24.
 */
@SpringBootApplication
@EnableRabbit // 开启rabbitMQ
@Slf4j
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
        log.info("服务启动成功");
    }
}
