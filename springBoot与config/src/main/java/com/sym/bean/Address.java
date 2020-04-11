package com.sym.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by 沈燕明 on 2019/1/6.
 */
@Data
@ToString
@Component
public class Address {

    /*
     * @Value注解可以映射配置文件获取单个值, 必须指定映射的配置名，用"${}"包裹,
     * 也可以使用"#{}"包裹表示计算表达式
     */

    @Value("${person.address.addressId:'addressId默认值'}")
    private String addressId;

    @Value("${person.address.country:'country默认值'}")
    private String country;

    @Value("${person.address.isDel:false}")
    private boolean isDel;

    @Value("#{2018+1}")
    private long count;


}
