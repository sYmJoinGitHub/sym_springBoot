package com.sym.mybatis.annotation;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springBoot与mybatis整合-注解版，对mybatis的额外配置
 * <p>
 *
 * @author ym.shen
 * @date 2018/11/10
 */
@Configuration
public class AnnotationConfig {

    /**
     * 通过springBoot提供的ConfigurationCustomizer(定制器),
     * 可以向springBoot自动配置mybatis的组件增加额外的配置;
     * 或者直接在配置文件application.yml中配置
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 设置日志
            configuration.setLogImpl(Slf4jImpl.class);
        };
    }
}
