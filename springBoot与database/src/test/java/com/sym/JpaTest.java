package com.sym;

import com.sym.jpa.JpaApplication;
import com.sym.jpa.JpaEntityRepository;
import com.sym.jpa.domain.JpaEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author shenym
 * @date 2020/3/25 10:23
 */
@SpringBootTest(classes = {JpaApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class JpaTest {

    @Autowired
    private JpaEntityRepository repository;

    @Test
    public void testInsert(){
        JpaEntity entity = new JpaEntity();
        entity.setName("test_01")
                .setRate(1.1256)
                .setSale(BigDecimal.valueOf(8.552658))
                .setDeleted(false)
                .setCreateTime(LocalDateTime.now());
        JpaEntity jpaEntity = repository.save(entity);
        log.info("返回自增主键id:{}", jpaEntity.getId());
    }

//    @Test
//    public void updateTest(){
//        Optional<JpaEntity> optionalJpaEntity = repository.findById(1L);
//        JpaEntity jpaEntity = optionalJpaEntity.get();
//        // 修改信息
//        jpaEntity.setName("update_test_0").setRate(0.9655);
//        repository.updateJpaEntityById(jpaEntity);
//    }

    @Test
    public void testQuery(){
        List<JpaEntity> list = repository.findJpaEntityByMoreThanSaleOrRate(BigDecimal.valueOf(0.1), 0.1);
        log.info("查询结果：{}", list);
    }
}
