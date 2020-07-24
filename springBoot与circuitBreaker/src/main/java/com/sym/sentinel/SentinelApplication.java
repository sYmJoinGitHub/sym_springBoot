package com.sym.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sym.sentinel.annotation.EnableSentinel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * {@link SentinelResource}不单单用于controller的接口流控。同时也可以用于方法上面
 *
 * @author shenyanming
 * Created on 2020/7/21 17:23
 */
@SpringBootApplication
@EnableSentinel
@Profile("sentinel")
@Slf4j
public class SentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class);
        log.info("服务启动成功");
    }
}
