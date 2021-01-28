package com.sym.config;

import com.sym.config.guava.GuavaCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * springBoot缓存知识点的配置类
 * Created by 沈燕明 on 2018/11/15.
 */
@Configuration
public class CacheConfig {

    public static final String GUAVA_CACHE_MANAGER = "guavaCacheManager";
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    @Primary
    @Bean(CAFFEINE_CACHE_MANAGER)
    public CacheManager caffeineCacheManager() {
        return new CaffeineCacheManager();
    }

    @Bean(GUAVA_CACHE_MANAGER)
    public CacheManager guavaCacheManager() {
        return new GuavaCacheManager();
    }
}
