package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用自定义的springBoot启动器starter
 *
 * Created by 沈燕明 on 2018/11/11.
 */
@SpringBootApplication
public class CustomStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run( CustomStarterApplication.class );
    }
}
