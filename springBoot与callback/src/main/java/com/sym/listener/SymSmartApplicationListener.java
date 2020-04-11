package com.sym.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SmartApplicationListener;

/**
 * 实现{@link ApplicationListener}只能监听一种事件, 如果实现{@link SmartApplicationListener}
 * 可以监听多种事件, 只要重写它的 supportsEventType() 方法即可
 *
 * @author shenym
 * @date 2020/3/29 23:22
 */

public class SymSmartApplicationListener implements SmartApplicationListener {

    /**
     * 返回true即表示对事件感兴趣
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationStartedEvent.class.isAssignableFrom(eventType) ||
                ApplicationFailedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 处理此事件
        System.err.println("SymSmartApplicationListener.onApplicationEvent()");
    }
}
