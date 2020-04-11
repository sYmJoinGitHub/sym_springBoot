package com.sym.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springBoot执行定时任务的启动类
 *
 * Created by 沈燕明 on 2018/11/28.
 */
@SpringBootApplication
@EnableScheduling // 开启定时任务的功能
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
