package com.sym.config;

import com.sym.config.component.SimpleFilter;
import com.sym.config.component.SimpleListener;
import com.sym.config.component.SimpleServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * springBoot知识点-内嵌Servlet的配置类
 * <p>
 * Created by 沈燕明 on 2018/10/31.
 */
@Configuration
public class ServletConfig {

    /**
     * 对Servlet容器的自定义配置，使用{@link WebServerFactoryCustomizer}
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> WebServerFactoryCustomizer() {
        return factory -> factory.setPort(8084);
    }

    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Servlet
     */
    @Bean(name = "servletRegistrationBean")
    public ServletRegistrationBean<SimpleServlet> myServletRegister() {
        ServletRegistrationBean<SimpleServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        // 注册自定义的Servlet
        servletRegistrationBean.setServlet(new SimpleServlet());
        // 添加自定义Servlet，要映射的URL地址
        servletRegistrationBean.setUrlMappings(Collections.singletonList("/servlet"));
        // 若对自定义的servlet有额外配置，可以通过setInitParameters()传递配置，
        // 该方法接收一个Map<String,String>参数
        Map<String, String> servletConfig = new HashMap<>();
        servletRegistrationBean.setInitParameters(servletConfig);
        return servletRegistrationBean;
    }


    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Filter
     */
    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean<SimpleFilter> myFilterRegister() {
        FilterRegistrationBean<SimpleFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 注册自定义的Filter
        filterRegistrationBean.setFilter(new SimpleFilter());
        // 添加自定义的Filter需要过滤的url地址
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/filter"));
        // 也可以指定需要过滤的Servlet
        // filterRegistrationBean.setServletNames();
        return filterRegistrationBean;
    }


    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Listener
     */
    @Bean(name = "servletListenerRegistrationBean")
    public ServletListenerRegistrationBean<SimpleListener> myListener() {
        ServletListenerRegistrationBean<SimpleListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        // 注册自定义的Listener
        servletListenerRegistrationBean.setListener(new SimpleListener());
        return servletListenerRegistrationBean;
    }


}
