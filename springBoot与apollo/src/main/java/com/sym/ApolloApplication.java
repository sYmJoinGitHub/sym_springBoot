package com.sym;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sym.listener.ApolloConfigListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenyanming
 * Created on 2020/7/8 09:42
 */
@SpringBootApplication
@EnableApolloConfig
@Slf4j
public class ApolloApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApolloApplication.class);
        application.run(args);
    }

    private static void apolloApi(){
        // 设置 apollo config-service的地址 (meta-service是和config-service放在一起的)
        System.setProperty("dev_meta", "http://localhost:13000");
        // 设置app.id, 对应apollo的项目
        System.setProperty("app.id", "sym_01");
        // 设置环境, 不同的环境有不同的Config-service服务
        System.setProperty("env", "DEV");
        // 设置集群
        System.setProperty("apollo.cluster", "beijing");
        // 获取不同命名空间
        Config appConfig = ConfigService.getConfig("db.config");
        // 获取命名空间下的配置
        String property = appConfig.getProperty("url", "1");
        log.info("获取配置db.config.url: {}", property);
    }
}
