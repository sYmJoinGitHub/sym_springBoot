package com.sym.mybatis.xml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * springBoot与mybatis整合-配置文件版的启动类
 *
 * @author ym.shen
 * @date 2018/11/11
 */
@SpringBootApplication
@ActiveProfiles("mybatis")
public class XmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(XmlApplication.class, args);
    }
}
