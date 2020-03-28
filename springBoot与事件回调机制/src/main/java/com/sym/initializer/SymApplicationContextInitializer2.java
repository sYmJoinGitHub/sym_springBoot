package com.sym.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@link ApplicationContextInitializer}是 SpringBoot 容器启动的初始化器, 在 IOC 容器
 * 初始化前被回调{@link ApplicationContextInitializer#initialize(ConfigurableApplicationContext)}.
 * 它可以在application.yml中添加key-value配置指定, 其中key为：context.initializer.classes. 以这种方式
 * 添加的初始化器优先级最高
 * 
 *
 * @author shenym
 * @date 2020/3/28 15:09
 */

public class SymApplicationContextInitializer2 implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    /**
     * 方法内可以初始化 IOC 容器, 例如添加自定义的环境属性
     * @param applicationContext IOC容器
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer2");
    }
}
