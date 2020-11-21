package com.sym.bboss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Bboss配置类，将自定义的两个组件注册注入IOC实现{@link com.sym.bboss.annotation.BbossMapper}扫描和实例化工作
 *
 * Created by shenYm on 2019/8/17.
 */
@Configuration
//@ImportResource(value = "classpath:biz.properties")
public class BbossConfig {

    @Autowired
    private Environment env;

    @Bean
    public InstantiationAwareBeanPostProcessor BbossInstantiationAwareBeanPostProcessor(){
        return new BbossInstantiationAwareBeanPostProcessor();
    }

    @Bean
    public BeanDefinitionRegistryPostProcessor SymBeanDefinitionRegistryPostProcessor(){
        return new SymBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
