package com.sym.bboss;

import com.sym.bboss.bean.AccountBean;
import com.sym.bboss.mapper.BankMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 沈燕明 on 2019/8/4.
 */
@SpringBootTest(classes = BbossApplication.class)
@RunWith(SpringRunner.class)
public class BbossServiceByMapper {

    @Autowired
    private BankMapper bankMapper;

    @Test
    public void testOne(){
        List<AccountBean> accountListByAgeAndCity = bankMapper.getAccountListByAgeAndCity(12, "Shaft");
        System.out.println(accountListByAgeAndCity);
    }

    @Test
    public void testTwo(){
        AccountBean accountByNumber = bankMapper.getAccountByNumber(25);
        System.out.println(accountByNumber);
    }

    @Test
    public void testThree(){
        List<AccountBean> accountListByAge = bankMapper.getAccountListByAge(32);
        System.out.println(accountListByAge);
    }
}
