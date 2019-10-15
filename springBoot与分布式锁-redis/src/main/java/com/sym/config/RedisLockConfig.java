package com.sym.config;

import com.sym.service.RedisOperations;
import com.sym.service.impl.DefaultRedisOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 由于springBoot默认的RedisTemplate是用 JdkSerializationRedisSerializer 去序列化key
 * 这会导致保存在redis的键值对被序列化成字节，不易阅读。我们自己注入一个RedisTemplate，并
 * 改用StringRedisSerializer来序列化。
 * <p>
 * Created by 沈燕明 on 2019/5/30 9:54.
 */
@Configuration
public class RedisLockConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        // 我们要自己回调，重新设置
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 如果用户没有自定义{@link RedisOperations}实现类, 则使用默认的实现类{@link DefaultRedisOperations}
     * @param stringRedisTemplate springBoot操作redis的工具模板
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedisOperations.class)
    public RedisOperations redisOperations(StringRedisTemplate stringRedisTemplate) {
        return new DefaultRedisOperations(stringRedisTemplate);
    }
}
