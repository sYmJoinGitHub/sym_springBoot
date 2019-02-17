package com.sym.concurrenMap;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * springBoot缓存知识点的配置类
 * Created by 沈燕明 on 2018/11/15.
 */
@Configuration
public class CacheConfig {

    /**
     * 使用匿名实现类的方式自定义 keyGenerator 即缓存key的生成策略，将其注册到IOC容器中
     * @return
     */
    @Bean(name = "mykeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            /**
             * @param target 方法所在类的实例对象
             * @param method 将被调用的方法
             * @param params 将被调用的方法的参数
             */
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return params[0];
            }
        };
    }
}
