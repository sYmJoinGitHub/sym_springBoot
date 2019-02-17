package com.sym.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ApplicationRunner 会在IOC容器初始化并且加载所有组件后被回调，
 * 它是从IOC容器中获取到的，所以需要将它注册到IOC容器
 * <p>
 * Created by 沈燕明 on 2019/1/5.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 方法会传入包装后的命令行参数ApplicationArguments
        List<String> optionArgs = args.getNonOptionArgs();
        optionArgs.stream().forEach(System.out::println);
        System.err.println("MyApplicationRunner的run()方法...");
    }
}
