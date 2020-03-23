package com.sym.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * SpringApplicationRunListener也需要配置META-INF/的spring.factories文件
 * springBoot在启动过程中扫描其实现类，并在适当时候回调相应的方法
 * <p>
 * Created by 沈燕明 on 2019/1/5.
 */
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    // 规定要创建一个有参构造器，并且参数为：SpringApplication和String[]
    public MySpringApplicationRunListener(SpringApplication application, String[] args) {

    }

    @Override
    public void starting() {
        // 开始初始IOC容器前就会被回调
        System.err.println("MySpringApplicationRunListener的starting()方法...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        // 初始化环境就会被回调
        System.err.println("MySpringApplicationRunListener的environmentPrepared()方法...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        // 初始化IOC容器前会被回调
        System.err.println("MySpringApplicationRunListener的contextPrepared()方法...");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        // IOC容器初始化前会被回调
        System.err.println("MySpringApplicationRunListener的contextLoaded()方法...");

    }

//    @Override
//    public void finished(ConfigurableApplicationContext context, Throwable exception) {
//        // springBoot应用启动完成后会被回调
//        System.err.println("MySpringApplicationRunListener的finished()方法...");
//
//    }
}
