package com.sym.service;

/**
 * springBoot与dubbo集成，服务消费方的接口
 *
 * Created by 沈燕明 on 2018/12/2.
 */
public interface UserService {

    void getOrder();

    void sendToOrder(String msg);

}
