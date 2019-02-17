package com.sym.servlet.config;

import com.sym.servlet.config.filter.MyFilter;
import com.sym.servlet.config.listener.MyListener;
import com.sym.servlet.config.servlet.MyServlet;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * springBoot知识点-内嵌Servlet的配置类
 *
 * Created by 沈燕明 on 2018/10/31.
 */
@Configuration
public class MyConfig {

    /**
     * 对Servlet容器的自定义配置，使用EmbeddedServletContainerCustomizer接口
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setPort(8084); // 设置端口
                container.setContextPath("/sym"); // 设置项目根路径
                // ....还有其它设置
            }
        };
    }

    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Servlet
     * @return
     */
    @Bean(name = "servletRegistrationBean")
    public ServletRegistrationBean myServletRegister(){
        ServletRegistrationBean srBean = new ServletRegistrationBean();
        // 注册自定义的Servlet
        srBean.setServlet( new MyServlet() );
        // 添加自定义Servlet，要映射的URL地址
        srBean.setUrlMappings(Arrays.asList("/servlet"));
        // 若对自定义的servlet有额外配置，可以通过setInitParameters()传递配置，
        // 该方法接收一个Map<String,String>参数
        Map<String,String> servletConfig = new HashMap<>();
        srBean.setInitParameters( servletConfig );
        return srBean;
    }


    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Filter
     * @return
     */
    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean myFilterRegister(){
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        // 注册自定义的Filter
        frBean.setFilter( new MyFilter());
        // 添加自定义的Filter需要过滤的url地址
        frBean.setUrlPatterns(Arrays.asList("/filter"));
        // 也可以指定需要过滤的Servlet
        // frBean.setServletNames();
        return frBean;
    }


    /**
     * 往springBoot内嵌的Servlet容器中添加自定义的Listener
     * @return
     */
    @Bean(name = "servletListenerRegistrationBean")
    public ServletListenerRegistrationBean<ServletContextListener> myListener(){
        ServletListenerRegistrationBean<ServletContextListener> srBean = new ServletListenerRegistrationBean();
        // 注册自定义的Listener
        srBean.setListener( new MyListener());
        return srBean;
    }


}
