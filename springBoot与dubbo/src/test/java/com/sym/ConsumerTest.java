package com.sym;

import com.sym.consumer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shenyanming
 * Created on 2020/7/21 17:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testOne() {
        userService.getOrder();
    }

    @Test
    public void testTwo() {
        userService.setOrder("您好订单已经处理成功");
    }
}
