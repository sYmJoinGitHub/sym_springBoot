package com.sym.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author shenyanming
 * @date 2020/6/4 22:57.
 */
@Service
@Slf4j
public class KafkaService {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka processMessage start");
        log.info("processMessage, topic = {}, msg = {}", record.topic(), record.value());

        // do something ...

        log.info("kafka processMessage end");
    }

    /**
     * 发送消息
     * @param topic
     * @param data
     */
    public void sendMessage(String topic, String data) {
        log.info("kafka sendMessage start");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("kafka sendMessage success topic = {}, data = {}",topic, data);
            }
        });
        log.info("kafka sendMessage end");
    }

    /**
     * kafka事务
     * @param topic
     * @param data
     */
    public void transactionSend(String topic, String data){
        kafkaTemplate.executeInTransaction((operations)->{
            operations.send(topic, "data1");
            if("error".equals(data)){
                throw new RuntimeException("");
            }
            operations.send(topic, "data2");
            return true;
        });
    }

    /**
     * kafka事务, 通过直接加上事务注解也可以
     */
    @Transactional
    public void transactionSend(String topic){
        kafkaTemplate.send(topic, "data1");
        if(!StringUtils.isEmpty(topic)){
            throw new RuntimeException("");
        }
        kafkaTemplate.send(topic, "data2");
    }
}
