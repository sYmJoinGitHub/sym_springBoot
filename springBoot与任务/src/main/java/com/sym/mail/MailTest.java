package com.sym.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 沈燕明 on 2018/11/30.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testOne(){
        mailService.sendSimpleMail();
    }

    @Test
    public void testTwo() throws Exception{
        mailService.sendHtmlMail();
    }
}
