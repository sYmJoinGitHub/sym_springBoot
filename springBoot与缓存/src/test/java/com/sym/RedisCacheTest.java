package com.sym;

import com.sym.redis.domain.RoleEntity;
import com.sym.redis.service.RedisTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author shenym
 * @date 2020/3/24 10:32
 */
@SpringBootTest
public class RedisCacheTest {
    @Autowired
    private RedisTemplateService redisService;

    /**
     * 操作字符串数据的测试
     */
    @Test
    public void testOne() {
        redisService.opsString();
    }

    /**
     * 操作对象的测试
     */
    @Test
    public void testTwo() {
        redisService.opsObject();
    }


    @Test
    public void testFour() {
        RedisTemplate<Object, RoleEntity> redisTemplate = redisService.getRedisTemplate(RoleEntity.class);
        ValueOperations<Object, RoleEntity> operations = redisTemplate.opsForValue();
        operations.set("role", new RoleEntity(110, "警察", "无可匹敌"));
    }
}
