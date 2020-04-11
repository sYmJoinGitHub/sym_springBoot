package com.sym.redis.config;

import com.sym.redis.domain.CustomerEntity;
import com.sym.redis.domain.RoleEntity;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

/**
 * 整合redis的配置类
 *
 * @author shenym
 * @date 2020/3/24 10:25
 */
@Configuration
public class RedisConfig {

    /**
     * 当我们需要缓存对象时，默认的模板类 RedisTemplate，是按照JDK序列化规则来缓存数据，导致缓存到redis
     * 的数据会变成类似:\xAC\xED\x00\x05t\x00\x04user的格式，很不利于阅读。
     * <p>
     * 所以我们可以仿照 redis的自动配置类 RedisAutoConfiguration 往容器中注入我们自己的模板类
     * 并指定序列化器为 Jackson2JsonRedisSerializer ，需要引入Jackjson的jar包先
     */
    @Bean("roleRedisTemplate")
    public RedisTemplate<Object, Object> roleRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<RoleEntity> redisSerializer = new Jackson2JsonRedisSerializer<>(RoleEntity.class);
        template.setDefaultSerializer(redisSerializer);
        return template;
    }

    /**
     * 创建专属于{@link com.sym.redis.domain.CustomerEntity}的RedisTemplate
     */
    @Bean("CustomerRedisTemplate")
    public RedisTemplate<Object, Object> UserRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<CustomerEntity> redisSerializer = new Jackson2JsonRedisSerializer<>(CustomerEntity.class);
        template.setDefaultSerializer(redisSerializer);
        return template;
    }


    /**
     * 自定义 RedisCacheManager
     */
    @Bean
    public RedisCacheManager customerCacheManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .withCacheConfiguration("customerCacheManager", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)))
                .build();
    }

    /**
     * 订制版的CacheManager
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("cache1",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
                .withCacheConfiguration("cache2",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));

    }
}
