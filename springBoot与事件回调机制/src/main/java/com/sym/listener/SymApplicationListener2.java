package com.sym.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * {@link ApplicationListener}是spring原生的, 不是 springBoot 才有的,
 * 它可以作为监听器存在的, 所以只要有它感兴趣的事件发生就会被回调. 当它的
 * 泛型定义为{@link ApplicationContext}, 不管啥事件它都会被回调. 它可以
 * 通过 application.yml 配置
 *
 * @author shenym
 * @date 2020/3/29 22:58
 */
public class SymApplicationListener2 implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        System.err.println("SymApplicationListener2.onApplicationEvent()");
    }
}
