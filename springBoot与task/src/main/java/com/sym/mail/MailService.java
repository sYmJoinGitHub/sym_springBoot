package com.sym.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件发送的service
 * Created by 沈燕明 on 2018/11/30.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSenderImpl sender;

    /**
     * 发送简单的邮件
     */
    public void sendSimpleMail(){
        // 先注入 JavaMailSenderImpl
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("qaz542569199@163.com");
        message.setSubject("晚上八点-不见不散");
        message.setTo("542569199@qq.com");
        message.setText("九点零五分，就出去嗨皮");
        sender.send(message);
    }

    /**
     * 发送复杂邮件
     */
    public void sendHtmlMail() throws MessagingException {
        // 使用 JavaMailSenderImpl 获取一个MimeMessage对象
        MimeMessage message = sender.createMimeMessage();
        // 通过mimeMessageHelper封装上面获取到的MimeMessage对象
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom("qaz542569199@163.com");
        mimeMessageHelper.setSubject("晚上八点-不见不散");
        mimeMessageHelper.setTo("542569199@qq.com");
        // 设置以HTML格式发送邮件
        mimeMessageHelper.setText("<span style='color:red'>九点零五分，就出去嗨皮</span>",true);
        // 设置附件
        mimeMessageHelper.addAttachment("good.jpg",new File("D:\\用户图片\\20.jpg"));
        // 发送
        sender.send(message);
        System.out.println("邮件发送成功");
    }
}
