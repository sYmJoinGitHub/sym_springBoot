package com.sym.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author shenyanming
 * @date 2020/6/4 22:54.
 */
@SpringBootApplication
@Profile("kafka")
@EnableKafka
@Slf4j
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
        log.info("服务启动成功");
    }
}
