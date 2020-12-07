package com.sym.rabbitmq.service;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ组件化
 *
 * @author shenyanming
 * @date 2020/12/8 7:01.
 */
@Service
public class RabbitAdminService {

    /**
     * springBoot自动配置的，用于创建or删除：交换器exchange、队列queue、绑定关系bind
     */
    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 使用 springBoot提供的 amqpAdmin 可以创建or删除 交换器、队列和他们之间的绑定
     */
    public void createExchangeAndQueue() {
        // 创建交换器，参数意思依次是：交换器名、是否持久化、是否自动删除、额外参数
        Exchange exchange = new DirectExchange("springBoot.exchange", true, false, null);
        amqpAdmin.declareExchange(exchange);

        // 创建队列，参数意思依次是：队列名、是否持久化、是否独占、是否自动删除、额外参数
        Queue queue = new Queue("springBoot.queue", true, false, false, null);
        amqpAdmin.declareQueue(queue);

        // 绑定刚才创建的交换器和队列，注意写法： exchange ->绑定-> queue
        Binding binding = new Binding("springBoot.queue", Binding.DestinationType.QUEUE, "springBoot.exchange", "springBoot", null);
        amqpAdmin.declareBinding(binding);
    }

    public void deleteExchangeAndQueue() {
        // 删除绑定
        Binding binding = new Binding("springBoot.queue", Binding.DestinationType.QUEUE, "springBoot.exchange", "springBoot", null);
        amqpAdmin.removeBinding(binding);

        // 删除队列
        amqpAdmin.deleteQueue("springBoot.queue");

        // 删除交换器
        amqpAdmin.deleteExchange("springBoot.exchange");
    }
}
