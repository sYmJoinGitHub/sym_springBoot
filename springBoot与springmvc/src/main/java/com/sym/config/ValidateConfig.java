package com.sym.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Hibernate-Validate校验器的配置类
 *
 * @author shenyanming
 * @date 2020/8/3 22:07.
 */
@Configuration
public class ValidateConfig {

    @Bean
    public Validator simpleValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                // 快速失败模式
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
