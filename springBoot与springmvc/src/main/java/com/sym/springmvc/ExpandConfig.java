package com.sym.springmvc;
import com.sym.springmvc.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springBoot 1.x 扩展springmvc实现 WebMvcConfigurerAdapter 抽象类
 * springBoot 2.x 扩展springmvc实现 WebMvcConfigurer 接口
 * <p>
 * Created by 沈燕明 on 2018/10/15.
 */

@Configuration
public class ExpandConfig implements WebMvcConfigurer {

    /**
     * 扩展拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* 1.先实例化我们的自定义拦截器类 */
        MyInterceptor interceptor = new MyInterceptor();
        /* 2.将我们自定义的拦截器类添加到注册器中 */
        registry.addInterceptor(interceptor);
    }


    /**
     * 扩展视图映射
     * 扩展了视图映射就算没有Controller，也可以接受前端的请求
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
         * 引入thymeleaf的依赖，会自动将视图映射的前缀配置成classpath:template/，以及后缀配置成.html
         * 所以我们在添加视图的时候，只要指定html页面的名字即可(前提是页面需要放到template目录下)
         */
        registry.addViewController("/").setViewName("index");
    }

}
