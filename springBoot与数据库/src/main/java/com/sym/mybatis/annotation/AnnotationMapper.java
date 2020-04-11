package com.sym.mybatis.annotation;

import com.sym.mybatis.domain.SimpleEntity;
import org.apache.ibatis.annotations.*;

/**
 * springBoot与mybatis整合-注解版
 * 使用Mapper接口开发还跟ssm框架一样，可以每个Mapper接口类使用@Mapper注解，也可以在
 * 启动类或者mybatis配置类上使用{@link org.mybatis.spring.annotation.MapperScan}，指定该目录下所有接口为Mapper接口，
 * 从而省去一个一个接口类写{@link org.apache.ibatis.annotations.Mapper}
 * <p>
 *
 * @author ym.shen
 * @date 2018/11/10
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
