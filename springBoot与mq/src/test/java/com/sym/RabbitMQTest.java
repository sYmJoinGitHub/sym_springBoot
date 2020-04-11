package com.sym;

import com.sym.domain.ProductEntity;
import com.sym.rabbitmq.RabbitMQService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 沈燕明 on 2018/11/24.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMQTest {

    @Autowired
    private RabbitMQService rabbitMQService;

    /**
     * 测试：发送数据到类型为direct的转换器上
     */
    @Test
    public void testOne() {
        // 类型为direct的转换器，绑定队列时的路由键与发送消息的routingKey一模一样才可以
        String routingKey = "product_one";
        ProductEntity productBean = new ProductEntity(01, "笔记本", 16.25, "挺不错的", false);
        rabbitMQService.sendToProduct_direct(routingKey, productBean);
    }


    /**
     * 测试：发送数据到类型为fanout的转换器上
     */
    @Test
    public void testTwo() {
        // fanout类型的转换器为群发，不需要指定路由键
        ProductEntity productBean = new ProductEntity(02, "可口可乐", 2.36, "真难喝", false);
        rabbitMQService.sendToProduct_fanout(productBean);
    }


    /**
     * 测试：发送数据到类型为topic的转换器上
     */
    @Test
    public void testThree() {
        ProductEntity productBean = new ProductEntity(03, "1060ti显卡", 2455.12, "我喜欢", true);
        String routingKeyOne = "abc_one";
        String routingKeyTwo = "abc_three";
        String routingKeyThree = "abc_two";
        rabbitMQService.sendToProduct_topic(routingKeyOne, productBean);
        rabbitMQService.sendToProduct_topic(routingKeyTwo, productBean);
        rabbitMQService.sendToProduct_topic(routingKeyThree, productBean);
    }


    /**
     * 测试：从指定队列上获取数据
     */
    @Test
    public void testFour() {
        // 提供队列名
        String queue_one = "queue_one";
        String queue_two = "queue_two";
        String queue_three = "queue_three";
        String queue_four = "queue_four";
        // 获取数据
        Object objOne = rabbitMQService.receiveFromQueue(queue_one);
        System.out.println("队列queue_one获取的数据为：" + objOne);
        Object objTwo = rabbitMQService.receiveFromQueue(queue_two);
        System.out.println("队列queue_two获取的数据为：" + objTwo);
        Object objThree = rabbitMQService.receiveFromQueue(queue_three);
        System.out.println("队列queue_three获取的数据为：" + objThree);
        Object objFour = rabbitMQService.receiveFromQueue(queue_four);
        System.out.println("队列queue_three获取的数据为：" + objFour);

    }


    /**
     * 测试：创建exchange、queue、binding
     */
    @Test
    public void testFive() {
        rabbitMQService.createExchangeAndQueue();
    }


    /**
     * 测试：删除exchange、queue、binding
     */
    @Test
    public void testSix() {
        rabbitMQService.deleteExchangeAndQueue();
    }


}
