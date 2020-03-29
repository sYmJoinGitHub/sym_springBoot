package com.sym;

import com.sym.initializer.SymApplicationContextInitializer1;
import com.sym.listener.SymApplicationListener1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动 springBoot 就可以看到整个事件回调机制
 */
@SpringBootApplication
public class EventCallbackApplication {
    public static void main(String[] args) {
        // 手动创建 springBoot的上下文
        SpringApplication springApplication = new SpringApplication(EventCallbackApplication.class);
        // 手动添加系统初始化器
        springApplication.addInitializers(new SymApplicationContextInitializer1());
        // 手动添加监听器
        springApplication.addListeners(new SymApplicationListener1());
        // 手动springBoot启动容器
        springApplication.run(args);
    }
}

