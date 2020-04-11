package com.sym;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 国际化知识点配置类
 *
 * Created by 沈燕明 on 2018/10/20.
 */
@Configuration
public class I18nConfig {

    /**
     * 自定义国际化配置解析器
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/login");
        return messageSource;
    }

    /**
     * 自定义国际化处理器
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
