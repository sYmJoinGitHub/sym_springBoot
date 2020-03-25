package com.sym;

import com.sym.redis.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * dubbo集成的测试类
 *
 * Created by 沈燕明 on 2018/12/2.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DubboTest {

    @Autowired
    private UserService userService;

    @Test
    public void testOne(){
        userService.getOrder();
    }

    @Test
    public void testTwo(){
        userService.setOrder("您好订单已经处理成功");
    }
}
