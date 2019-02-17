package com.sym.rabbitmq;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rabbitMQ的启动类
 *
 * Created by 沈燕明 on 2018/11/24.
 */
@SpringBootApplication
@EnableRabbit // 开启rabbitMQ的注解
public class RabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class,args);
    }
}
