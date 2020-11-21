package com.sym.bboss.mapper;


import com.sym.bboss.annotation.BbossMapper;
import com.sym.bboss.bean.AccountBean;

import java.util.List;

/**
 * customer mapper
 * <p>
 * Created by shenym on 2019/8/22.
 */
@BbossMapper(value = "bankMapper.xml", index = "bank")
public interface BankMapper {

    /**
     * 根据accountNumber精确匹配一个用户
     *
     * @param accountNumber
     * @return
     */
    AccountBean getAccountByNumber(int accountNumber);

    /**
     * 根据firstName模糊搜索用户
     *
     * @param firstName
     * @return
     */
    List<AccountBean> getAccountListFuzzyByFirstName(String firstName);

    /**
     * 通过年龄搜索accountList
     *
     * @param age
     * @return
     */
    List<AccountBean> getAccountListByAge(int age);

    /**
     * 通过年龄和城市搜索用户信息
     *
     * @param age
     * @param city
     * @return
     */
    List<AccountBean> getAccountListByAgeAndCity(int age, String city);

}
