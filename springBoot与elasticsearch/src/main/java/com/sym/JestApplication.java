package com.sym;

import io.searchbox.client.JestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 沈燕明 on 2018/11/26.
 */
@SpringBootApplication
public class JestApplication {
    public static void main(String[] args) {
        SpringApplication.run(JestClient.class);
    }
}
