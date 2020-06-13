package com.sym;

import com.sym.jpa.JpaApplication;
import com.sym.jpa.repository.JpaEntityCrudRepository;
import com.sym.jpa.domain.JpaEntity;
import com.sym.jpa.repository.JpaEntitySpecificationExecutor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * jpa测试
 *
 * @author shenym
 * @date 2020/3/25 10:23
 */
@SpringBootTest(classes = {JpaApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class JpaTest {

    @Autowired
    private JpaEntityCrudRepository crudRepository;

    @Autowired
    private JpaEntitySpecificationExecutor specificationExecutor;

    @Test
    public void insert(){
        JpaEntity entity = new JpaEntity();
        entity.setName("test_01")
                .setRate(1.1256)
                .setSale(BigDecimal.valueOf(8.552658))
                .setDeleted(false)
                .setCreateTime(LocalDateTime.now());
        JpaEntity jpaEntity = crudRepository.save(entity);
        log.info("返回自增主键id:{}", jpaEntity.getId());
    }

    @Test
    public void update(){
        Optional<JpaEntity> optional = crudRepository.findById(1L);
        if(optional.isPresent()){
            JpaEntity entity = optional.get();
            entity.setName("update_test").setDeleted(Boolean.TRUE);
            // jpa的save()方法也可以更新数据
            crudRepository.save(entity);
        }
    }

    @Test
    public void find(){
        List<JpaEntity> list = crudRepository.findJpaEntityByMoreThanSaleOrRate(BigDecimal.valueOf(0.1), 0.1);
        log.info("查询结果：{}", list);
    }

    @Test
    public void sort(){
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        Iterable<JpaEntity> iterable = specificationExecutor.findAll(sort);
        iterable.forEach(entity -> {
            log.info("查询结果: {}", entity);
        });
    }

    /**
     * 复杂查询
     */
    @Test
    public void query(){
        specificationExecutor.findOne((root, query, cb)->{
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(cb.greaterThanOrEqualTo(root.get("")/*字段名*/, ""/*字段值*/));
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        });
    }
}
