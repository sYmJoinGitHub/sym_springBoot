package com.sym;

import com.sym.kafka.KafkaApplication;
import com.sym.kafka.service.KafkaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shenyanming
 * @date 2020/6/4 22:59.
 */
@SpringBootTest(classes = KafkaApplication.class)
@RunWith(SpringRunner.class)
public class KafkaTest {

    @Autowired
    private KafkaService kafkaService;

    @Test
    public void test01(){
        kafkaService.sendMessage("demo", "hello kafka");
    }
}
