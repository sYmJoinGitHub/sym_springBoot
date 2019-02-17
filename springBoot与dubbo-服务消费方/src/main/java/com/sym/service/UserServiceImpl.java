package com.sym.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * springBoot与dubbo集成，服务消费方的接口实现类
 *
 * Created by 沈燕明 on 2018/12/2.
 */
@Service
public class UserServiceImpl implements UserService {


    @Reference //@Reference注解可以远程连接服务提供方的接口，获取数据
    private OrderService orderService;

    @Override
    public void getOrder() {
        // 调用远程接口的方法
        System.out.println(orderService.gerOrder());
    }

    @Override
    public void sendToOrder(String msg) {
        // 调用远程接口的方法
        orderService.getMsg(msg);
    }
}
