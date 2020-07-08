package com.sym.bean;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 配置类
 *
 * @author shenyanming
 * Created on 2020/7/8 14:37
 */
@Data
@EnableApolloConfig
public class SymConfigBean {
    /**
     * namespace: application
     */
    @ApolloConfig
    private Config config;

    /**
     * namespace: db.config
     */
    @ApolloConfig("db.config")
    private Config dbConfig;

    /**
     * 将json字符串解析为实体类
     */
    @ApolloJsonValue("${jsonBeanProperty:[]}")
    private List<JsonBean> anotherJsonBeans;

    /**
     * 直接使用{@link org.springframework.beans.factory.annotation.Value}
     */
    @Value("${batch:100}")
    private int batch;
}
