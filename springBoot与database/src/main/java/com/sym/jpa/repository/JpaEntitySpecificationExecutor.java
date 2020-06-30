package com.sym.jpa.repository;

import com.sym.jpa.domain.JpaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.Predicate;

/**
 * JPA的复杂语句查询, 继承{@link JpaSpecificationExecutor}, 通过
 * {@link Specification}构造出{@link Predicate}, 可以实现任意复杂语句查询.
 * 如果想使用分页和排序, 还可以继承{@link PagingAndSortingRepository}
 *
 * @author shenyanming
 * Created on 2020/6/13 10:26
 */
@Repository
public interface JpaEntitySpecificationExecutor extends JpaSpecificationExecutor<JpaEntity>,
        PagingAndSortingRepository<JpaEntity, Long> {
}
