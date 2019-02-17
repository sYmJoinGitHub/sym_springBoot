package com.sym.mybatis.anon;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * springBoot与mybatis整合-注解版
 * 使用Mapper接口开发还跟ssm框架一样，可以每个Mapper接口类使用@Mapper注解，也可以在
 * 启动类或者mybatis配置类上使用@MapperScan注解，指定该目录下所有接口为Mapper接口，从而省去一个一个接口类写@Mapper注解
 *
 * Created by 沈燕明 on 2018/11/10.
 */
public interface MybatisAnonMapper {

    @Insert("insert into t_springboot(name) value(#{name})")
    @Options(useGeneratedKeys = true,keyProperty ="id")
    int add(MybatisAnonBean bean);

    @Delete("delete from t_springboot where id=#{id}")
    int delete(String id);

    @Select("select * from t_springboot where id=#{id}")
    MybatisAnonBean selectOne(String id);
}
