package com.sym.runner;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * {@link ApplicationRunner} 会在 {@link ApplicationContext}
 * 初始化并且刷新完被回调, 需要把它注册到容器中, 还可以使用{@link Order}指定调用顺序.
 * 它与{@link CommandLineRunner}的不同在于它的run()方法会对main()方法的运行参数进一
 * 步封装成{@link ApplicationArguments}
 *
 * @author shenym
 * @date 2020/3/29 22:45
 */
@Component
@Order(1)
public class SymApplicationRunner implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        // 方法会传入包装后的命令行参数ApplicationArguments
        Set<String> optionNames = args.getOptionNames();
        for(String optionArg : optionNames){
            List<String> optionValues = args.getOptionValues(optionArg);
            System.err.println("SymApplicationRunner：" + optionArg + "->" + optionValues);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
