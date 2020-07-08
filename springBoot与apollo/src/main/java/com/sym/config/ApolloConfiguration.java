package com.sym.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sym.bean.SymConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenyanming
 * Created on 2020/7/8 14:42
 */
@Configuration
public class ApolloConfiguration {

    /**
     * 将自定义的配置类注册到ioc, apollo会将配置信息自动注入到这个bean里面
     */
    @Bean
    public SymConfigBean symConfigBean() {
        return new SymConfigBean();
    }
}
