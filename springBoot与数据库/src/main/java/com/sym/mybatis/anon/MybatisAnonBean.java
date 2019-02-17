package com.sym.mybatis.anon;

import lombok.Getter;
import lombok.Setter;

/**
 * springBoot与mybatis整合-注解版的实体类
 * Created by 沈燕明 on 2018/11/10.
 */
public class MybatisAnonBean {

    @Setter @Getter
    private Integer id;
    @Setter @Getter
    private String name;

}
