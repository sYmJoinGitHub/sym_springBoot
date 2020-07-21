package com.sym.consumer.service;

import com.sym.api.OrderApi;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * springBoot与dubbo集成，服务消费方的接口实现类
 *
 * @author shenyanming
 * @date 2018/12/2
 */
@Service("userService")
public class UserService {

    /**
     * {@link Reference}注解可以远程连接服务提供方的接口，获取数据
     */
    @Reference
    private OrderApi orderApi;

    public void getOrder() {
        // 调用远程接口的方法
        System.out.println(orderApi.getOrder());
    }

    public void setOrder(String msg) {
        // 调用远程接口的方法
        orderApi.setOrder(msg);
    }
}
