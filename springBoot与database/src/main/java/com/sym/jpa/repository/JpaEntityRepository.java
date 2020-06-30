package com.sym.jpa.repository;

import com.sym.jpa.domain.JpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * 继承{@link JpaRepository}接口，就可以使用Spring Data提供的统一接口来进行数据库操作
 * JpaRepository接口有两个泛型，第一个是表示操作的实体类，第二个是实体类主键的变量类型。
 * 或者也可以简单继承{@link org.springframework.data.repository.CrudRepository}.
 *
 * @author ym.shen
 * @date 2018/11/11
 */
@Repository("jpaEntityRepository1")
public interface JpaEntityRepository extends JpaRepository<JpaEntity, Long> {

    /**
     * 自定义的查询方法, 也可以使用原生SQL语句
     *
     * @param sale sale
     * @param rate rate
     * @return 符合条件的集合
     */
    @Query(value = "select * from t_jpa t where t.sale >= :sale0 or t.rate >= :rate0", nativeQuery = true)
    List<JpaEntity> findJpaEntityByMoreThanSaleOrRate(@Param("sale0") BigDecimal sale, @Param("rate0") double rate);

    /**
     * 根据id批量删除
     *
     * @param idList id集合
     * @return SQL影响的行数
     */
    int deleteAllByIdIn(List<Integer> idList);

    /**
     * 手动修改信息
     *
     * @param jpaEntity 待修改的内容
     * @return SQL影响的行数
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "UPDATE JpaEntity set isDeleted=:#{#entity.isDeleted} where name=:#{#entity.name}")
    int updateEntity(@Param("entity") JpaEntity jpaEntity);
}
