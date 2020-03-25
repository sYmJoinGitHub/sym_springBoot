package com.sym.rabbitmq;

import com.sym.domain.ProductEntity;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * rabbitMQ的操作类，转换器和队列以及它们之间的绑定信息如下：
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
 */
@Service
public class RabbitMQService {

    /**
     * springBoot自动配置的，用于简化操作rabbitMQ的模板类
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * springBoot自动配置的，用于创建or删除：交换器exchange、队列queue、绑定关系bind
     */
    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 发送消息到 product_direct 转换器上，根据路由键完全匹配才分发到对应的队列中
     * @param routingKey 路由键
     * @param object 具体的信息内容
     */
    public void sendToProduct_direct(String routingKey,Object object){
        // send()方法，需要我们自定义message的消息头和消息体
        // rabbitTemplate.send();

        // 可以使用spring封装好的转换方法 convertAndSend()
        rabbitTemplate.convertAndSend("product_direct",routingKey,object);
    }


    /**
     * 发送消息到 product_fanout 转换器上，因为fanout类型的转换器是群发的，所以不需要指定路由键
     */
    public void sendToProduct_fanout(Object object){
        rabbitTemplate.convertAndSend("product_fanout","",object);
    }


    /**
     * 发送消息到 product_topic 转换器上，它是根据通配符匹配，来确定分发哪些队列上
     */
    public void sendToProduct_topic(String routingKey, Object object){
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


    /**
     * 监听队列，每当队列中有消息放进去时，就会自动调用这个方法，将消息内的信息，放到方法参数上去
     * 因为此方法参数指定为 ProductBean ，所以springBoot会将消息转换成 ProductBean。
     */
    @RabbitListener(queues = "queue_four")
    public void productListener(ProductEntity product){
        System.out.println("收到消息啦，product="+product);
    }


    /**
     * {@link RabbitListeners} 注解可以设置多个 @RabbitListener，用来监听多个队列
     */
    @RabbitListeners(value = {
            @RabbitListener(queues = "queue_two"),
            @RabbitListener(queues = "queue_three")
    })
    public void messageListener(Message message){
        System.out.println("收到消息啦，message="+message.getMessageProperties());
    }


    /**
     * 使用 springBoot提供的 amqpAdmin 可以创建or删除 交换器、队列和他们之间的绑定
     */
    public void createExchangeAndQueue(){
        // 创建交换器，参数意思依次是：交换器名、是否持久化、是否自动删除、额外参数
        Exchange exchange = new DirectExchange("springBoot.exchange",true,
                false,null);
        amqpAdmin.declareExchange(exchange);

        // 创建队列，参数意思依次是：队列名、是否持久化、是否独占、是否自动删除、额外参数
        Queue queue = new Queue("springBoot.queue",true,
                false,false,null);
        amqpAdmin.declareQueue(queue);

        // 绑定刚才创建的交换器和队列，注意写法： exchange ->绑定-> queue
        Binding binding = new Binding("springBoot.queue",Binding.DestinationType.QUEUE,
                "springBoot.exchange", "springBoot",null);
        amqpAdmin.declareBinding(binding);
    }

    public void deleteExchangeAndQueue(){
        // 删除绑定
        Binding binding = new Binding("springBoot.queue",Binding.DestinationType.QUEUE,"springBoot.exchange",
                "springBoot",null);
        amqpAdmin.removeBinding(binding);

        // 删除队列
        amqpAdmin.deleteQueue("springBoot.queue");

        // 删除交换器
        amqpAdmin.deleteExchange("springBoot.exchange");
    }
}
