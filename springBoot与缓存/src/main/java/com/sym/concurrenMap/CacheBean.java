package com.sym.concurrenMap;

import lombok.Getter;
import lombok.Setter;

/**
 * springBoot缓存知识点的JavaBean
 * <p>
 * Created by 沈燕明 on 2018/11/15.
 */
public class CacheBean {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String name;

    public CacheBean() {
    }

    public CacheBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
