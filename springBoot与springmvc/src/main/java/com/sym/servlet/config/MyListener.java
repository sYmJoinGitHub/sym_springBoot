package com.sym.servlet.config;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 自定义Servlet的监听器，需要实现具体的监听器接口，Servlet的监听器有：
 *
 *      ServletContextListener -- 监听Servlet容器(即ServletContext)的初始化和销毁，即web应用启动和停止的监听
 *      ServletContextAttributeListener -- 监听Servlet容器(即ServletContext)变量的添加、删除、修改
 *
 *      HttpSessionListener -- 监听HttpSession的创建和销毁
 *      HttpSessionAttributeListener -- 监听HttpSession作用域中变量的添加、删除、修改
 *      HttpSessionActivationListener -- 监听HttpSession活化(从硬盘到内存)和钝化(从内存到硬盘)
 *
 *      ServletRequestListener -- 监听ServletRequest的创建和销毁
 *      ServletRequestAttributeListener -- 监听ServletRequest作用域中变量的添加、删除、修改
 *
 * 本例子是监听ServletContext，即Servlet容器中创建和销毁，也是web应用的启动和停止
 * Created by 沈燕明 on 2018/10/31.
 */
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("web应用启动了！！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("web应用停止了！！");
    }
}
