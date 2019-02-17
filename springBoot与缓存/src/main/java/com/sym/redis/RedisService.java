package com.sym.redis;
import com.sym.redis.bean.RoleBean;
import com.sym.redis.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * 整合redis的缓存操作类
 * <p>
 * Created by 沈燕明 on 2018/11/22.
 */
@Service
public class RedisService {

    /* 注入springBoot默认的专门用于操作String类型的模板类 StringRedisTemplate */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /* 注入springBoot默认的redis模板类 RedisTemplate */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /* 注入我们自定义的模板类 RedisTemplate，注意使用的注解是 @Resource */
    @Resource(name = "roleRedisTemplate")
    private RedisTemplate<Object, Object> roleRedisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 缓存字符串
     */
    public void opsString() {
        // 设置一个缓存
        stringRedisTemplate.opsForValue().set("user", "沈燕明");
        // 获取一个缓存
        String value = stringRedisTemplate.opsForValue().get("user");
    }

    /**
     * 缓存对象
     */
    public void opsObject() {
        RoleBean role = new RoleBean(110, "警察", "执法单位");
        ValueOperations<Object, Object> valueOperations = roleRedisTemplate.opsForValue();
        valueOperations.set("role", role);
    }


    /**
     * 使用注解缓存
     *
     * @return
     */
    @CachePut(value = "user", key = "#result.userId", cacheManager = "userCacheManager")
    public UserBean setUser() {
        UserBean user = new UserBean(001, "沈燕明", "123", false);
        return user;
    }


    /**
     * 根据传入的类型获取只属于该类型的RedisTemplate，就不用一个实体类一个模板类的去注入了
     * @param t
     * @param <T>
     * @return
     */
    public <T> RedisTemplate<Object, T> getRedisTemplate(Class<T> t) {
        RedisTemplate<Object, T> template = null;
        try {
            template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            Jackson2JsonRedisSerializer<T> redisSerializer = new Jackson2JsonRedisSerializer<>(t);
            template.setDefaultSerializer(redisSerializer);
            // 这一步要自己手动调，不然会报空指针异常。不懂为啥放在配置类就不会出事情
            template.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }


}
