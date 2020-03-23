package com.sym;

import com.sym.myself.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: shenym
 * @Date: 2019-03-08 9:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {

    @Autowired
    private LogService logService;

    @Test
    public void test() throws Exception {
        logService.printLog();
    }

}
