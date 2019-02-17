package com.sym.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * springBoot的配置文件application.yml只有对数据源的基本配置(username、url、password...)
 * 如果我们自己要整合第三方的数据源，则需要额外的配置，需要我们自己创建一个数据源配置，将配置好
 * 的数据源注入到IOC容器中
 *
 * Created by 沈燕明 on 2018/11/9.
 */
@Configuration
public class DruidConfig {

    /**
     * 配置自己的druid数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidSource(){
        DruidDataSource dataSource =  new DruidDataSource();
        // 需要配置 DruidDataSource ，也可以初始化后自己设置，但是
        // 这种配置太麻烦了，可以直接将配置写在application.yml中。
        // 用springBoot的 @ConfigurationProperties 注解来绑定
        // dataSource.setKeepAlive();
        return dataSource;
    }

    /**
     * 配置Druid的监控---配置自带的Servlet
     * @return
     */
    @Bean(name = "StatViewServlet")
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
        // 对自定义servlet的配置可以通过 setInitParameters()方法设置，
        Map<String,String> servletConfigMap = new HashMap<>();
        servletConfigMap.put("allow","127.0.0.1"); // 允许访问的主机地址
        servletConfigMap.put("loginUsername","admin"); // 登录用户名
        servletConfigMap.put("loginPassword","admin"); // 登录密码

        bean.setInitParameters(servletConfigMap);
        return bean;
    }

    /**
     * 配置Druid的监控---配置自带的Filter
     * @return
     */
    @Bean(name = "WebStatFilter")
    public FilterRegistrationBean druidFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean( new WebStatFilter());
        bean.addUrlPatterns("/*"); // Filter的拦截路径
        // 额外的配置
        Map<String,String> filterConfigMap = new HashMap<>();
        // 不拦截以下url映射
        filterConfigMap.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters( filterConfigMap );
        return bean;
    }
}
