package com.sym.datasource.c3p0;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * springBoot的配置文件application.yml只有对数据源的基本配置(username、url、password...)
 * 如果我们自己要整合第三方的数据源，则需要额外的配置，需要我们自己创建一个数据源配置，将配置好
 * 的数据源注入到IOC容器中
 *
 * Created by 沈燕明 on 2018/11/10.
 */
@Configuration
public class C3p0Config {

    //@ConfigurationProperties(prefix = "spring.datasource")
    // 因为此次项目既存在Druid又c3p0，它们同时绑定前缀为spring.datasource的配置，所以使用时要注释掉一个
    @Bean(name = "ComboPooledDataSource")
    public DataSource c3p0DataSource(){
        return new ComboPooledDataSource();
    }
}
