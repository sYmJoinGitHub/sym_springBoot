package com.sym.rabbitmq.service;

import com.sym.domain.ProductEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rabbitMQ的消费者，转换器和队列以及它们之间的绑定信息如下：
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
 * @date 2020/12/8 7:00.
 */
@Service
public class RabbitConsumer {

    /**
     * springBoot自动配置的，用于简化操作rabbitMQ的模板类
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 从队列中获取数据
     *
     * @param queueName 从这个队列中获取
     */
    public Object receiveFromQueue(String queueName) {
        Object o = rabbitTemplate.receiveAndConvert(queueName);
        if (null != o) System.out.println("从队列:" + queueName + "获取到的数据类型为：" + o.getClass());
        return o;
    }


    /**
     * 监听队列，每当队列中有消息放进去时，就会自动调用这个方法，将消息内的信息，放到方法参数上去
     * 因为此方法参数指定为 ProductBean ，所以springBoot会将消息转换成 ProductBean。
     */
    @RabbitListener(queues = "queue_four")
    public void productListener(ProductEntity product) {
        System.out.println("收到消息啦，product=" + product);
    }


    /**
     * {@link RabbitListeners} 注解可以设置多个 @RabbitListener，用来监听多个队列
     */
    @RabbitListeners(value = {
            @RabbitListener(queues = "queue_two"),
            @RabbitListener(queues = "queue_three")
    })
    public void messageListener(Message message) {
        System.out.println("收到消息啦，message=" + message.getMessageProperties());
    }

}
