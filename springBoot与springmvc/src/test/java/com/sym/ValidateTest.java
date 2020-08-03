package com.sym;

import com.sym.validate.ValidateService;
import com.sym.vo.CustomerVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author shenyanming
 * @date 2020/8/3 22:20.
 */
@SpringBootTest(classes = MvcApplication.class)
@RunWith(SpringRunner.class)
public class ValidateTest {

    @Autowired
    private ValidateService validateService;

    @Test
    public void test01(){
        CustomerVO customerVO = CustomerVO.builder().age(1).name(null).build();
        List<String> list = validateService.validate(customerVO);
        list.forEach(System.out::println);
    }
}
