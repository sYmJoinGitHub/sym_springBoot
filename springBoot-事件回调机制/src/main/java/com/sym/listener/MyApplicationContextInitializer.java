package com.sym.listener;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ApplicationContextInitializer 需要配置在META-INF/spring.factories文件下
 * springBoot在启动中会扫描加载所有实现ApplicationContextInitializer接口的实现类，
 * 并回调initialize()方法
 *
 * Created by 沈燕明 on 2019/1/5.
 */
public class MyApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 方法参数 ConfigurableApplicationContext 就是IOC容器
        System.err.println("自定义的MyApplicationContextInitializer执行了...");
    }
}
