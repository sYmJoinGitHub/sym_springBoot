package com.sym.redis;
import com.sym.redis.bean.RoleBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Created by 沈燕明 on 2018/11/22.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisService redisService;

    /**
     * 操作字符串数据的测试
     */
    @Test
    public void testOne(){
        redisService.opsString();
    }

    /**
     * 操作对象的测试
     */
    @Test
    public void testTwo(){
        redisService.opsObject();
    }

    /**
     *  使用缓存注解
     */
    @Test
    public void testThree() throws Exception{
        redisService.setUser();
    }

    @Test
    public void testFour(){
        RedisTemplate<Object, RoleBean> redisTemplate = redisService.getRedisTemplate(RoleBean.class);
        ValueOperations<Object, RoleBean> operations = redisTemplate.opsForValue();
        operations.set("role",new RoleBean(110,"警察","无可匹敌"));
    }
}
