package com.sym.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 沈燕明 on 2018/11/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void testOne(){

    }
}
