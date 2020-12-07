package com.sym.rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rabbitMQ的生产者，转换器和队列以及它们之间的绑定信息如下：
 * 有3个转换器：product_direct(direct类型)、product_fanout(fanout类型)、product_topic(topic类型)
 * 有4个队列：queue_one、queue_two、queue_three、queue_four
 * 它们之间的绑定关系如下：
 *          product_direct:   -- routing_key=product_one 绑定queue_one
 *                            -- routing_key=product_two 绑定queue_two
 *                            -- routing_key=product_three 绑定queue_three
 *                            -- routing_key=product_four 绑定queue_four
 *
 *         product_fanout:    --全部绑定，没有设置routing_key，因为它是群发的
 *
 *         product_topic:     -- routing_key=*_one 绑定queue_one
 *                            -- routing_key=#_two 绑定queue_two
 *                            -- routing_key=*_three 绑定queue_three
 *                            -- routing_key=# 绑定queue_four
 * @author shenyanming
 * @date 2020/12/8 6:59.
 */
@Service
public class RabbitProducer {

    /**
     * springBoot自动配置的，用于简化操作rabbitMQ的模板类
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到 product_direct 转换器上，根据路由键完全匹配才分发到对应的队列中
     * @param routingKey 路由键
     * @param object 具体的信息内容
     */
    public void sendToProductDirect(String routingKey, Object object){
        // send()方法，需要我们自定义message的消息头和消息体
        // rabbitTemplate.send();

        // 可以使用spring封装好的转换方法 convertAndSend()
        rabbitTemplate.convertAndSend("product_direct",routingKey,object);
    }


    /**
     * 发送消息到 product_fanout 转换器上，因为fanout类型的转换器是群发的，所以不需要指定路由键
     */
    public void sendToProductFanout(Object object){
        rabbitTemplate.convertAndSend("product_fanout","",object);
    }


    /**
     * 发送消息到 product_topic 转换器上，它是根据通配符匹配，来确定分发哪些队列上
     */
    public void sendToProductTopic(String routingKey, Object object){
        rabbitTemplate.convertAndSend("product_topic",routingKey,object);
    }


    /**
     * 从队列中获取数据
     * @param queueName 从这个队列中获取
     */
    public Object receiveFromQueue(String queueName){
        Object o = rabbitTemplate.receiveAndConvert(queueName);
        if(  null != o ) System.out.println("从队列:"+queueName+"获取到的数据类型为："+o.getClass());
        return o;
    }
}
