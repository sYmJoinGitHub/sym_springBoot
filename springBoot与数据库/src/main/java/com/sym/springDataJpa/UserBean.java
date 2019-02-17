package com.sym.springDataJpa;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * springBoot与springData JPA整合的实体类
 * 需要使用JPA规范的注解来确定实体类与表的映射关系
 *
 * Created by 沈燕明 on 2018/11/11.
 */
@Entity
@Table( name = "t_springboot")
public class UserBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
