package com.sym.rabbitmq;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ的配置类
 * Created by 沈燕明 on 2018/11/24.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 默认情况，消息转换器也是以JDK的序列化器来序列化实体类，所以我们需要向容器注入一个
     * MessageConverter，让它以JSON的格式序列化实体类
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
