package com.sym.springSecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 通过实现 UserDetailsService 接口，我们可以获取用户的登陆信息
 *
 * Created by 沈燕明 on 2019/5/30 15:35.
 */
@Component
public class UserDetailsServiceConfig implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceConfig.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("当前用户登陆为：{}",username);
        // 一般在这里查询数据库找出用户的信息，将其组装成一个UserDetails对象，然后交于springSecurity校验
        // UserDetails是一个接口，要么自己实现一个，要么使用springSecurity自带的User

        // 一般数据库是保存用户密码密文，因此返回的UserDetails的密码就必须为加密后的用户密码，
        // 不过我们这边没有读取数据库，所以就模拟加密

        return new User(username,passwordEncoder.encode("123456"),true,
                true,true,true, AuthorityUtils.NO_AUTHORITIES);
    }
}
