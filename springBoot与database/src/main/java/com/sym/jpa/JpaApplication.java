package com.sym.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * jpa启动类
 *
 * @author ym.shen
 * @date 2018/11/11
 */
@SpringBootApplication
@ActiveProfiles("jpa")
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
}
