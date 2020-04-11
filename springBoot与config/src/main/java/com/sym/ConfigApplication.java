package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * Created by 沈燕明 on 2019/1/6.
 */
@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ConfigApplication.class);
        // 通过硬编码方式增加属性
        Properties prop = new Properties();
        prop.setProperty("sym.key", "prop_1");
        springApplication.setDefaultProperties(prop);
        // 启动容器
        springApplication.run(args);
    }
}
