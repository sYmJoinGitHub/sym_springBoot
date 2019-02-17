package com.sym.redis;

import com.sym.redis.bean.RoleBean;
import com.sym.redis.bean.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * 整合redis时的配置类
 * Created by 沈燕明 on 2018/11/22.
 */
@Configuration
public class RedisConfig {

    /**
     * 当我们需要缓存对象时，默认的模板类 RedisTemplate，是按照JDK序列化规则来缓存数据，导致缓存到redis
     * 的数据会变成类似:\xAC\xED\x00\x05t\x00\x04user的格式，很不利于阅读。
     * <p>
     * 所以我们可以仿照 redis的自动配置类 RedisAutoConfiguration 往容器中注入我们自己的模板类
     * 并指定序列化器为 Jackson2JsonRedisSerializer ，需要引入Jackjson的jar包先
     *
     * @param redisConnectionFactory
     * @return
     * @throws
     */
    @Bean("roleRedisTemplate")
    public RedisTemplate<Object, Object> roleRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<RoleBean> redisSerializer = new Jackson2JsonRedisSerializer<>(RoleBean.class);
        template.setDefaultSerializer(redisSerializer);
        return template;
    }

    /**
     * 创建专属于UserBean的RedisTemplate
     *
     * @param redisConnectionFactory
     * @return
     * @throws
     */
    @Bean("UserRedisTemplate")
    public RedisTemplate<Object, Object> UserRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<UserBean> redisSerializer = new Jackson2JsonRedisSerializer<>(UserBean.class);
        template.setDefaultSerializer(redisSerializer);
        return template;
    }


    /**
     * 当我们使用缓存注解时，就需要用到CacheManager来管理缓存，引入redis后，就适配RedisCacheManager来作为
     * 缓存管理器，springBoot默认在 RedisCacheConfiguration 配置类中已经帮我们注入了RedisCacheManager，
     * 它底层还是使用模板类 RedisTemplate 来操作redis，这也导致了它会使用JDK序列化规则来序列化对象，
     * 同样，为了让它的序列化规则变为JSON，我们仿照springBoot生成RedisCacheManager的方式，创建
     * 自定义的缓存管理器
     *
     * @param roleRedisTemplate
     * @return
     */
    @Bean
    @Primary // 当注入多个缓存管理器，需要指定其中一个为默认缓存管理器，建议是springBoot自动配置的那个缓存管理器
    public RedisCacheManager roleCacheManager(RedisTemplate<Object, Object> roleRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(roleRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }


    /**
     * 专属于UserBean的缓存管理器
     *
     * @param UserRedisTemplate
     * @return
     */
    @Bean("userCacheManager")
    public RedisCacheManager userCacheManager(RedisTemplate<Object, Object> UserRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(UserRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
