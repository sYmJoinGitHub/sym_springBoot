package com.sym.mybatis.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springBoot与mybatis整合-注解版
 *
 * Created by 沈燕明 on 2018/11/10.
 */
@SpringBootApplication
@MapperScan(value = "com.sym.mybatis.anon") // MapperScan避免了每个接口都要写@Mapper注解的尴尬
public class AnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }
}
