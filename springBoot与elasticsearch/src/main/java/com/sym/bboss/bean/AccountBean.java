package com.sym.bboss.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 继承 ESBaseData 以获取ES的元数据
 * <p>
 * Created by 沈燕明 on 2018/11/16.
 */
@Data
@ToString
public class AccountBean{
    @JsonProperty(value = "account_number")
    private Integer accountNumber;
    private Integer balance;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private Integer age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;
}
