package com.sym.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 沈燕明 on 2019/1/6.
 */
@Data
@ToString
// @ConfigurationProperties注解可以映射application.yml中的配置到实体类中，该注解有个prefix参数用于指定配置文件中的前缀
@ConfigurationProperties(prefix = "person")
public class Person {
    /* 整型数据绑定 */
    private int id;

    /* 配置文件带双引号的字符串绑定 */
    private String userName;

    /* 配置文件带单引号的字符串绑定 */
    private String realName;

    /* 日期类型的配置 */
    private Date date;

    /* 布尔类型的绑定 */
    private boolean isDel;

    /* 对象类型绑定 */
    private Address address;

    /* Map类型绑定 */
    private Map<String, Object> maps;

    /* List类型绑定 */
    private List<String> lists;
}
