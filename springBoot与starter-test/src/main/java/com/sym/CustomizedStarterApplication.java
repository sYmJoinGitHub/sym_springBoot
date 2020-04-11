package com.sym;

import com.sym.annotation.EnableSymTemplate;
import com.sym.component.SymTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 自定义启动器的启动类
 *
 * @author ym.shen
 * Created on 2020/4/11 11:16
 */
@SpringBootApplication
@EnableSymTemplate
public class CustomizedStarterApplication {
    public static void main(String[] args) {
        // 创建容器
        ConfigurableApplicationContext context = SpringApplication.run(CustomizedStarterApplication.class, args);
        // 通过添加和去掉 @EnableSymTemplate 注解查看组件是否注册成功
        SymTemplate symTemplate = context.getBean(SymTemplate.class);
        symTemplate.doRun();
    }
}
