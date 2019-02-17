package com.sym.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * CommandLineRunner 会在IOC容器初始化并且加载所有组件后被回调，
 * 它是从IOC容器中获取到的，所以需要将它注册到IOC容器
 * Created by 沈燕明 on 2019/1/5.
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
        System.err.println("MyCommandLineRunner的run()方法...");
    }
}
