package com.sym.runner;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * {@link CommandLineRunner}同{@link ApplicationRunner}一样,
 * 都是在{@link org.springframework.context.ApplicationContext}初始化并且刷新完被回调,
 * 它会后于{@link ApplicationRunner}执行, 也是需要注册到容器里面
 *
 * @author shenym
 * @date 2020/3/29 22:48
 */
@Component
public class SymCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        Arrays.stream(args).forEach(System.out::println);
        System.err.println("SymCommandLineRunner.run()");
    }
}
