package com.sym.jpa.repository;

import com.sym.jpa.domain.JpaEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link JpaSpecificationExecutor}用于jpa的复杂查询
 * {@link PagingAndSortingRepository}用于分页和排序
 *
 * @author shenyanming
 * Created on 2020/6/13 10:26
 */
public interface JpaEntitySpecificationExecutor extends JpaSpecificationExecutor<JpaEntity>,
        PagingAndSortingRepository<JpaEntity, Long> {
}
