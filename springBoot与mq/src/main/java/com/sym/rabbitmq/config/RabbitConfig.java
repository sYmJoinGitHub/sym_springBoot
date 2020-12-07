package com.sym.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过创建Bean组件, 实现交换器和队列的绑定
 *
 * @author shenyanming
 * @date 2020/12/8 6:52.
 */
@Configuration
public class RabbitConfig {

    /**
     * 创建交换器
     *
     * @return 主题交换器
     * @see org.springframework.amqp.core.DirectExchange
     * @see org.springframework.amqp.core.HeadersExchange
     * @see org.springframework.amqp.core.FanoutExchange
     * @see org.springframework.amqp.core.CustomExchange
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("product_direct");
    }

    /**
     * 创建队列
     *
     * @return 队列
     */
    @Bean
    Queue queue() {
        return new Queue("common");
    }

    /**
     * 执行绑定
     *
     * @return 路由绑定
     */
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("order");
    }

    /* 定制化 */

    /**
     * 默认情况，消息转换器也是以JDK的序列化器来序列化实体类，所以向容器
     * 注入一个MessageConverter，让它以JSON的格式序列化实体类
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
