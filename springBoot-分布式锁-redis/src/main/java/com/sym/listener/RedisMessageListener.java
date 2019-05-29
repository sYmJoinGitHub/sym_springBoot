package com.sym.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 监听Redis的通道，在有发送key时，
 *
 * @Auther: shenym
 * @Date: 2019-04-02 10:48
 */
@Configuration
public class RedisMessageListener {

    /**
     * redis监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // 表示监听redis-lock这个通道，用 messageListenerAdapter 这个适配器去处理通道的消息
        container.addMessageListener(messageListenerAdapter,new PatternTopic("redis-lock"));
        container.addMessageListener(messageListenerAdapter,new PatternTopic("__keyevent@0__:expired"));
        // 可以添加另外的监听通道
        // ...
        return container;
    }

    /**
     * redis消息处理器
     * @return
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        // 指定处理通道消息的类和方法
        return new MessageListenerAdapter(new RedisMessageResolver(),"lockHandler");
    }
}
