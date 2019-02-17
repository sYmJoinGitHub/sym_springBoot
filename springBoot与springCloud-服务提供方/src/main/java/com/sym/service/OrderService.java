package com.sym.service;
/**
 * springCloud服务提供方，对外提供服务的接口
 *
 * Created by 沈燕明 on 2018/12/3.
 */
public interface OrderService {

    /**
     * 对外传递数据
     * @return
     */
    String getOrder();

    /**
     * 从外接收数据
     * @param order
     */
    void receiveOrder(String order);
}
