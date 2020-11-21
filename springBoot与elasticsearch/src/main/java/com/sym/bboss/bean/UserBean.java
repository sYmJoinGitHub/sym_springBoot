package com.sym.bboss.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frameworkset.orm.annotation.ESId;
import org.frameworkset.elasticsearch.entity.ESBaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenym on 2019/8/8 10:41.
 */
public class UserBean extends ESBaseData {


    @JsonProperty("customer_id")
    @ESId
    private long customerId;

    @JsonProperty("full_name")
    private String fullName;

    public UserBean(long id,String name){
        this.customerId = id;
        this.fullName = name;
    }


    public static List<UserBean> getList(){
        List<UserBean> retList = new ArrayList<>();

        retList.add(new UserBean(110,"警察"));
        retList.add(new UserBean(120,"医生"));
        retList.add(new UserBean(119,"消防员"));


        return retList;
    }
}
