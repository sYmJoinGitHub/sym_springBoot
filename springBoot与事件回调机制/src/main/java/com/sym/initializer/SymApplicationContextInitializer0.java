package com.sym.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ApplicationContextInitializer}是 SpringBoot 容器启动的初始化器, 在 IOC 容器
 * 初始化前被回调{@link ApplicationContextInitializer#initialize(ConfigurableApplicationContext)}.
 * 它可以配置在 classpath:/META-INF/spring.factories下, springBoot会自动加载
 *
 * @author shenym
 * @date 2020/3/28 15:09
 */

public class SymApplicationContextInitializer0 implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * 方法内可以初始化 IOC 容器, 例如添加自定义的环境属性
     * @param applicationContext IOC容器
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 获取当前环境配置
        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
        // 创建自定义的属性Map
        Map<String, Object> map = new HashMap<>();
        map.put("k0", "vv0");
        MapPropertySource mapPropertySource = new MapPropertySource("initializer0", map);
        // 添加到当前环境中
        propertySources.addLast(mapPropertySource);

        System.out.println("ApplicationContextInitializer0");
    }
}
