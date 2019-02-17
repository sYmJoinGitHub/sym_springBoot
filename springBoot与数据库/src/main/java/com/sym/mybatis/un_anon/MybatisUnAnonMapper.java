package com.sym.mybatis.un_anon;
import org.apache.ibatis.annotations.Mapper;

/**
 * springBoot与mybatis整合-配置文件版，接口类或用 @Mapper 注解标识，
 * 或全部用 @MapperScan 标注所有mapper的包路径
 *
 * Created by 沈燕明 on 2018/11/11.
 */
@Mapper
public interface MybatisUnAnonMapper {

    int add(MybatisUnAnonBean bean);

    MybatisUnAnonBean select(String id);
}
