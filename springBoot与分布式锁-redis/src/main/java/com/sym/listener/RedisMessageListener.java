package com.sym.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * springBoot 1.x 监听redis通道的配置;
 * 在使用redis当分布式锁场景下, 当解锁时, 会发布通道到“ _$redis_$lock(自定义的, 已经内置到脚本中) “
 * 这样监听此通道, 就可以及时唤醒阻塞在此Key上的线程
 *
 * @Auther: shenym
 * @Date: 2019-04-02 10:48
 */
@Configuration
public class RedisMessageListener {

    /**
     * 配置 redis 监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // 表示监听 _$redis_$lock 这个通道, 用 messageListenerAdapter 这个适配器去处理通道的消息
        container.addMessageListener(messageListenerAdapter,new PatternTopic("_$redis_$lock"));

        /*
         * 这里还可以添加多个监听通道, 全部加到监听容器里 RedisMessageListenerContainer
         * container.addMessageListener(messageListenerAdapter,new PatternTopic("__keyevent@0__:expired"));
         * container.addMessageListener(messageListenerAdapter,new PatternTopic("__keyevent@0__:del"));
         */

        return container;
    }

    /**
     * redis消息处理器, 这边会运用到 {@link #redisMessageListenerContainer(RedisConnectionFactory, MessageListenerAdapter)}方法上
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        // 指定处理通道消息的类和方法
        return new MessageListenerAdapter(new RedisMessageResolver(),"handlerMessage");
    }
}
