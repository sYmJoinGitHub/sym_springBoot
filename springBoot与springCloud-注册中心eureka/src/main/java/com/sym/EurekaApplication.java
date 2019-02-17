package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka注册中心启动
 *
 * Created by 沈燕明 on 2018/12/3.
 */
@SpringBootApplication
@EnableEurekaServer //启动springCloud的eureka功能
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run( EurekaApplication.class );
    }
}
