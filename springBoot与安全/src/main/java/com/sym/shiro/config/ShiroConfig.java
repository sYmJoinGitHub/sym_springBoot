package com.sym.shiro.config;

import com.sym.shiro.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * springBoot集成shiro的配置类
 *
 * @Auther: shenym
 * @Date: 2018-12-05 10:59
 */
@Configuration
public class ShiroConfig {

    /**
     * 1、需要创建shiro的核心,securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new MyRealm());
        return securityManager;
    }

    /**
     * 2、创建shiro的过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        // 设置securityManager
        factory.setSecurityManager(securityManager());
        // 设置跳转登录页面的url，请注意springBoot大部分情况不能直接跳转页面
        // 所以需要跳转到Controller，由Controller来跳转到登录页
        factory.setLoginUrl("/shiro/login");
        // 设置拦截规则，注意shiro的拦截规则与Servlet的拦截规则有点不同
        // Servlet匹配全路径都使用一个"*"，而shiro匹配全路径要使用两个"*"
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/shiro/login/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factory;
    }
}
