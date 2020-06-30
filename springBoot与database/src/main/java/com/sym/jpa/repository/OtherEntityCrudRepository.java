package com.sym.jpa.repository;

import com.sym.jpa.domain.OtherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 继承JpaRepository接口，就可以使用Spring Data提供的统一接口来进行数据库操作
 * JpaRepository接口有两个泛型，第一个是表示操作的实体类，第二个是实体类主键的变量类型
 *
 * @author ym.shen
 * @date 2018/11/11
 */
@Repository
public interface OtherEntityCrudRepository extends CrudRepository<OtherEntity, Long> {

}
