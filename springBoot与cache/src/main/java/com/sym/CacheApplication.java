package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring提供了许多注解用于处理缓存：{@link Cacheable}、{@link CachePut}...
 * 它们都会被{@link org.springframework.cache.interceptor.CacheAspectSupport}拦截
 *
 * @author shenyanming
 * Created on 2021/1/28 13:37
 */
@EnableCaching
@SpringBootApplication
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CacheApplication.class);
        application.run(args);
    }
}
