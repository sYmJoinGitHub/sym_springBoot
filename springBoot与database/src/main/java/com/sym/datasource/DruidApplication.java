package com.sym.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

/**
 * springBoot默认使用 hikariCP 数据源, 可以改为使用 druid 数据源
 *
 * @author ym.shen
 * @date 2018/11/10
 */
@SpringBootApplication
public class DruidApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DruidApplication.class);
        application.setAdditionalProfiles("druid");
        ConfigurableApplicationContext context = application.run(args);

        DataSource bean = context.getBean(DataSource.class);
        System.out.println(bean.getClass());

    }

}
