package com.sym.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 需要配置在 classpath:/META-INF/spring.factories 下, 它好似 spring 原生的
 * {@link org.springframework.context.ApplicationListener}, springBoot 做了一个升级版
 * {@link SpringApplicationRunListener}, 它的一些回调方法会在 springBoot 容器启动时被调用
 *
 * @author shenym
 * @date 2020/3/29 22:08
 */

public class SymSpringApplicationRunListener implements SpringApplicationRunListener {

    /**
     * 强制要求需要有这样一个构造方法
     */
    public SymSpringApplicationRunListener(SpringApplication application, String[] args){
    }

    @Override
    public void starting() {
        // 最开始回调的方法, SpringApplication.run()刚执行时就会被调用
        System.err.println("SymSpringApplicationRunListener.starting()");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        // ConfigurableEnvironment创建后, ApplicationContext创建之前, 回调此方法
        System.err.println("SymSpringApplicationRunListener.environmentPrepared()");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        // ApplicationContext创建完, refresh()方法执行前, 回调此方法
        System.err.println("SymSpringApplicationRunListener.contextPrepared()");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        // ApplicationContext完成加载, 在contextPrepared()回调后, refresh()方法执行前, 回调此方法
        System.err.println("SymSpringApplicationRunListener.contextLoaded()");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        // ApplicationContext刷新并启动后, 在CommandLineRunners和ApplicationRunner被调用前, 回调此方法
        System.err.println("SymSpringApplicationRunListener.started()");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        // springBoot所有操作都完成后, 整个run()方法结束前, 回调此方法, 不出异常情况下它最晚被回调
        System.err.println("SymSpringApplicationRunListener.running()");

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        // 容器启动失败回调此方法
        System.err.println("SymSpringApplicationRunListener.failed()");
    }
}
