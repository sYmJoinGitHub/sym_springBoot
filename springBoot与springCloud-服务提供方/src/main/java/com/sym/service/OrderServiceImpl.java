package com.sym.service;

import org.springframework.stereotype.Service;

/**
 * springCloud服务提供方，对外提供服务的接口实现类
 *
 * Created by 沈燕明 on 2018/12/3.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 对外传递数据
     * @return
     */
    @Override
    public String getOrder() {
        return "您好！有新的订单了";
    }

    /**
     * 从外接收数据
     * @param order
     */
    @Override
    public void receiveOrder(String order) {
        System.out.println("已处理订单："+order);
    }
}
