package com.sym.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@link ApplicationRunner} 会在 {@link org.springframework.context.ApplicationContext}
 * 初始化并且刷新完被回调. 需要把它注册到容器中
 *
 * @author shenym
 * @date 2020/3/29 22:45
 */
@Component
public class SymApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        // 方法会传入包装后的命令行参数ApplicationArguments
        List<String> optionArgs = args.getNonOptionArgs();
        optionArgs.forEach(System.out::println);
        System.err.println("SymApplicationRunner.run()");
    }
}
