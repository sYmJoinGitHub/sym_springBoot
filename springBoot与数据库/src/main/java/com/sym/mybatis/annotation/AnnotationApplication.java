package com.sym.mybatis.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * springBoot与mybatis整合-注解版
 *
 *
 * @author ym.shen
 * @date 2018/11/10
 */
@SpringBootApplication
@MapperScan(value = "com.sym.mybatis.anon")
@ActiveProfiles("mybatis")
public class AnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }
}
