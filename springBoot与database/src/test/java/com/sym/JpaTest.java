package com.sym;

import com.sym.jpa.JpaApplication;
import com.sym.jpa.domain.JpaEntity;
import com.sym.jpa.domain.OtherEntity;
import com.sym.jpa.repository.JpaEntityRepository;
import com.sym.jpa.repository.JpaEntitySpecificationExecutor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private JpaEntityRepository jpaEntityRepository1;

    @Autowired
    private JpaEntitySpecificationExecutor specificationExecutor;


    /**
     * 普通的插入操作
     */
    @Test
    public void insert() {
        JpaEntity entity = new JpaEntity();
        entity.setName("test_01")
                .setRate(1.1256)
                .setSale(BigDecimal.valueOf(8.552658))
                .setDeleted(false)
                .setCreateTime(LocalDateTime.now());
        JpaEntity jpaEntity = jpaEntityRepository1.save(entity);
        log.info("返回自增主键id:{}", jpaEntity.getId());
    }

    /**
     * 唯一索引重复插入
     */
    @Test
    public void repeatInsert() {
        JpaEntity entity = new JpaEntity();
        entity.setName("test_01").setId(11)
                .setRate(1.1256);
        try {
            JpaEntity jpaEntity = jpaEntityRepository1.save(entity);
            log.info("返回自增主键id:{}", jpaEntity.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("插入异常", e);
        }
    }

    /**
     * 普通修改操作, JPA的save()方法就是一个更新方法update()
     */
    @Test
    public void update() {
        Optional<JpaEntity> optional = jpaEntityRepository1.findById(1L);
        if (optional.isPresent()) {
            JpaEntity entity = optional.get();
            entity.setName("update_test").setDeleted(Boolean.TRUE);
            // jpa的save()方法也可以更新数据
            jpaEntityRepository1.save(entity);
        }
    }

    /**
     * 自定义SQL语句查询
     */
    @Test
    public void find() {
        List<JpaEntity> list = jpaEntityRepository1.findJpaEntityByMoreThanSaleOrRate(
                BigDecimal.valueOf(0.1), 0.1);
        log.info("查询结果：{}", list);
    }

    /**
     * 分页 or 排序查询
     */
    @Test
    public void sort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        Iterable<JpaEntity> iterable = specificationExecutor.findAll(sort);
        iterable.forEach(entity -> {
            log.info("查询结果: {}", entity);
        });
    }

    /**
     * 复杂查询, 通过{@link CriteriaQuery#from(Class)}可以关联表连接查询
     */
    @Test
    public void complexQuery() {
        Optional<JpaEntity> one = specificationExecutor.findOne((root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            // join t_other, 关联t_other表, 关联条件是 t_jpa.id = t_other.jpa_id
            Root<OtherEntity> joinRoot = query.from(OtherEntity.class);
            predicates.add(cb.equal(root.get(JpaEntity.Fields.id), joinRoot.get(OtherEntity.Fields.jpaId)));

            // other condition
            predicates.add(cb.equal(joinRoot.get(OtherEntity.Fields.id), 1L));

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        });
        JpaEntity entity = one.orElse(null);
        System.out.println(entity);
    }

    /**
     * 多表连接查询
     */
    @Test
    public void joinQuery() {
        List<JpaEntity> all = specificationExecutor.findAll((root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            Join<Object, Object> join = root.join("other", JoinType.LEFT);
            predicateList.add(cb.equal(join.get("desc"), "aaa"));
            return cb.and(predicateList.toArray(new Predicate[0]));
        });
        System.out.println(all);
    }

    /**
     * 方法参数为JavaBean的更新
     */
    @Test
    public void updateWithBean() {
        JpaEntity jpaEntity = new JpaEntity();
        jpaEntity.setName("test_01").setDeleted(true).setRate(100D);
        int i = jpaEntityRepository1.updateEntity(jpaEntity);
        log.info("影响行数: {}", i);
    }
}
