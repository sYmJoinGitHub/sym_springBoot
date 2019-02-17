package com.sym.servlet.config.filter;
import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义Servlet的过滤器，需要实现Filter接口，注意导入的是javax.Servlet下的Filter
 *
 * Created by 沈燕明 on 2018/10/31.
 */
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 监听器执行过滤的时候，执行的逻辑
        System.out.println("my filter process....自定义的过滤器生效了!");
        // doFilter()的意思是放行，不放行后面的逻辑执行不到
        chain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化，需要执行的逻辑
    }



    @Override
    public void destroy() {
        // 过滤器销毁时，需要执行的逻辑
    }
}
