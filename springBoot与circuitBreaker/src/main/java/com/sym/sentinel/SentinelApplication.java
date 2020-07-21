package com.sym.sentinel;

import com.sym.hystrix.HystrixApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * @author shenyanming
 * Created on 2020/7/21 17:23
 */
@SpringBootApplication
@Profile("sentinel")
@Slf4j
public class SentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class);
        log.info("服务启动成功");
    }
}
