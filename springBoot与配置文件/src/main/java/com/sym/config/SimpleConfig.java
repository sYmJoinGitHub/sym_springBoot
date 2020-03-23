package com.sym.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * 使用 @Configuration 告诉springBoot这是一个配置类
 *
 * @author 沈燕明
 */
@Configuration
/*
 * @PropertySource可以加载指定多个配置文件, 当相同属性名同时出现在application.yml和外部配置文件时,
 * springBoot以读取application.yml的配置为准
 */
@PropertySource(value = {"classpath:config/jdbc.properties", "classpath:config/redis.properties"})
/*
 * @ImportResource注解用来导入spring的配置文件
 */
@ImportResource(value = {"classpath:config/spring-bean.xml"})
public class SimpleConfig {

    @Autowired
    private Environment env;

    @Autowired
	@Qualifier(value = "symObj")
    private Object symObj;

    /**
     * 使用 @Bean 可以将方法返回值注入到springBoot的IOC容器中
     * 组件名默认是方法名,可以使用 @Bean 的name属性改变组件名
     */
    @Bean
    public String objectI() {
        return "new a Object";
    }

    /**
     * 注解{@link PropertySource}加载的配置就可以在spring环境中读取到
     */
    @PostConstruct
    public void doRead() {
        System.out.println(env.getProperty("jdbc.url"));
        System.out.println(env.getProperty("redis.host"));
    }


    /**
     * 注解{@link ImportResource}加载的配置就可以直接注入
     */
    @PostConstruct
    public void doGet() {
        System.out.println(symObj);
    }
}
