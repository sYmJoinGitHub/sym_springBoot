package com.sym.springSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * springBoot与SpringSecurity整合的配置类
 * <p>
 * Created by 沈燕明 on 2018/12/1.
 */
@EnableWebSecurity // 开启web的安全认证功能，里面已经加了@Configuration注解
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    // 重写父类方法实现对springSecurity的配置
}
