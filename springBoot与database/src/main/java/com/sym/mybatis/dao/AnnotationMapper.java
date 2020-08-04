package com.sym.mybatis.dao;

import com.sym.mybatis.domain.SimpleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 使用Mapper接口开发还跟ssm框架一样，可以每个Mapper接口类使用@Mapper注解，也可以在
 * 启动类或者mybatis配置类上使用{@link org.mybatis.spring.annotation.MapperScan}，
 * 指定该目录下所有接口为Mapper接口，从而省去一个一个接口类写{@link org.apache.ibatis.annotations.Mapper}
 *
 * @author shenyanming
 * @date 2020/8/4 22:16.
 */
public interface AnnotationMapper {

    @Insert("insert into t_springboot(name) value(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(SimpleEntity entity);

    @Delete("delete from t_springboot where id=#{id}")
    int delete(int id);

    @Select("select * from t_springboot where id=#{id}")
    SimpleEntity selectOne(int id);

}
