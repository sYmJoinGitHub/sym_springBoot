package com.sym.async;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springBoot中使用异步任务的启动类
 * Created by 沈燕明 on 2018/11/28.
 */
@SpringBootApplication
@EnableAsync //开启异步任务的功能
public class AsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class);
    }
}
