package com.sym.map.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * springBoot缓存知识点的JavaBean
 * <p>
 * Created by 沈燕明 on 2018/11/15.
 */
@Data
@AllArgsConstructor
@ToString
public class CacheBean {
    private Integer id;
    private String name;
}
