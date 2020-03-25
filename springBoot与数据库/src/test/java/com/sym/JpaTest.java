package com.sym;

import com.sym.jpa.JpaEntity;
import com.sym.jpa.JpaEntityRepository;
import com.sym.mybatis.domain.SimpleEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author shenym
 * @date 2020/3/25 10:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {

    @Autowired
    private JpaEntityRepository jpaEntityRepository;

    @Test
    public void saveTest(){
        jpaEntityRepository.save(new JpaEntity(3, "test"));
    }

    @Test
    public void selectTest(){
        Optional<JpaEntity> optional = jpaEntityRepository.findOne(Example.of(new JpaEntity(3, "test")));
        System.out.println(optional.get());
    }
}
