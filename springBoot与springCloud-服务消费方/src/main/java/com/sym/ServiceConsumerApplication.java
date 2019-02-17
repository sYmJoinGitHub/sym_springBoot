package com.sym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * springCloud服务消费方的启动类
 *
 * Created by 沈燕明 on 2018/12/3.
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启发现服务的功能
public class ServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class);
    }

    @Bean // RestTemplate是springCloud作为分布式交互的模板类
    @LoadBalanced // 开启负载均衡机制
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
