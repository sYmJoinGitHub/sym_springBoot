package com.sym.jpa;

import com.sym.jpa.domain.JpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 *  继承JpaRepository接口，就可以使用Spring Data提供的统一接口来进行数据库操作
 *  JpaRepository接口有两个泛型，第一个是表示操作的实体类，第二个是实体类主键的变量类型
 *
 *
 * @author ym.shen
 * @date 2018/11/11
 */
@Repository
public interface JpaEntityRepository extends JpaRepository<JpaEntity, Long> {

//    /**
//     * 自定义的jpa方法, 用来修改信息
//     * @param jpaEntity 修改类, 必须含有主键
//     */
//    @Query("update JpaEntity t set t.name=#{#entity.name},t.rate=#{#entity.rate},t.sale=#{#entity.rate}," +
//            "t.isDeleted=#{#entity.isDeleted} where t.id=#{#entity.id}")
//    @Modifying
//    void updateJpaEntityById(@Param("entity") JpaEntity jpaEntity);


    /**
     * 自定义的查询方法, 也可以使用原生SQL语句
     * @param sale sale
     * @param rate rate
     * @return 符合条件的集合
     */
    @Query(nativeQuery = true, value = "select * from t_jpa t where t.sale >= :sale0 or t.rate >= :rate0")
    List<JpaEntity> findJpaEntityByMoreThanSaleOrRate(@Param("sale0")BigDecimal sale, @Param("rate0")double rate);
}
