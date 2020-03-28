package com.sym;

import com.sym.initializer.SymApplicationContextInitializer1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventCallbackApplication {
    public static void main(String[] args) {
        // 手动创建 springBoot的上下文
        SpringApplication springApplication = new SpringApplication(EventCallbackApplication.class);
        // 手动添加系统初始化器
        springApplication.addInitializers(new SymApplicationContextInitializer1());
        // 手动springBoot启动容器
        springApplication.run(args);
    }
}

