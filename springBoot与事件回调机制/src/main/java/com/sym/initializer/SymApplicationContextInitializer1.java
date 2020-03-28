package com.sym.initializer;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@link ApplicationContextInitializer}是 SpringBoot 容器启动的初始化器, 在 IOC 容器
 * 初始化前被回调{@link ApplicationContextInitializer#initialize(ConfigurableApplicationContext)}.
 * 它可以在创建{@link SpringApplication}时调用它的
 * {@link SpringApplication#addInitializers(ApplicationContextInitializer[])}方法手动添加
 *
 *
 * @author shenym
 * @date 2020/3/28 15:09
 */

public class SymApplicationContextInitializer1 implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * 方法内可以初始化 IOC 容器, 例如添加自定义的环境属性
     * @param applicationContext IOC容器
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 可以获取 BeanFactory, 设置一些组件
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.getBeanNamesIterator();

        System.out.println("ApplicationContextInitializer1");
    }
}
