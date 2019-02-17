package com.sym.rabbitmq.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 发布消息时需要用到的实体类，也要实现序列化接口
 * <p>
 * Created by 沈燕明 on 2018/11/24.
 */
public class ProductBean implements Serializable {
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private Double price;
    @Setter
    @Getter
    private String describe;
    @Setter
    @Getter
    private Boolean isDiscount;

    public ProductBean() {
    }

    public ProductBean(Integer id, String name, Double price, String describe, Boolean isDiscount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.isDiscount = isDiscount;
    }

    @Override
    public String toString() {
        return "ProductBean{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", describe='" + describe + '\'' + ", isDiscount=" + isDiscount + '}';
    }
}
