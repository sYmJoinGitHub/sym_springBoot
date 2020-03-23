package com.sym;
import com.sym.bean.Address;
import com.sym.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Created by 沈燕明 on 2019/1/6.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(Person.class)
public class YmlTest {

    @Autowired
    private Person person;

    @Autowired
    private Address address;

    @Autowired
    private String objectI;

    /**
     * 测试 @ConfigurationProperties 和 @Value
     */
    @Test
    public void testOne(){
        System.out.println(person);
        System.out.println(address);
    }

    /**
     * 测试 @Configuration 和 @Bean
     */
    @Test
    public void testTwo(){
        System.out.println(objectI);
    }

    /**
     * 测试 配置文件的占位符
     */
    @Value("randomID")
    private String uuid;

    @Test
    public void testThree() {
        System.out.println(uuid);
    }


}
