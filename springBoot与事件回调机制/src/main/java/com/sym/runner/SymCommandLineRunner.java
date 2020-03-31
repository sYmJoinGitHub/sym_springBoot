package com.sym.runner;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * {@link CommandLineRunner}同{@link ApplicationRunner}一样,
 * 都是在{@link ApplicationContext}初始化并且刷新完被回调.
 * 可以通过{@link Order}指定调用顺序, 它会原封不动地携带系统运行的参数(main()方法)
 *
 * @author shenym
 * @date 2020/3/29 22:48
 */
@Component
@Order(1)
public class SymCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        if (args.length > 0) {
            System.err.println("SymCommandLineRunner：" + Arrays.toString(args));
        }
    }
}
