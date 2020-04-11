package com.sym.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: shenym
 * @Date: 2018-12-05 11:34
 */
@Controller
@RequestMapping("shiro")
public class ShiroController {

    /**
     * 跳转登录页使用
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     * 用户登录使用
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(String name,String pwd){
        UsernamePasswordToken token = new UsernamePasswordToken(name,pwd);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "main";
    }


    /**
     * 登出
     */
    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }


    /**
     * 认证访问
     */
    @RequestMapping("main")
    public String main(){
        return "main";
    }
}
