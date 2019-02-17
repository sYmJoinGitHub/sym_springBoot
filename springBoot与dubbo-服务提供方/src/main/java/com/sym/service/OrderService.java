package com.sym.service;
/**
 * dubbo服务提供方，对外发布的接口
 *
 * Created by 沈燕明 on 2018/12/2.
 */
public interface OrderService {
    /**
     * 对外发布：表示获取一个订单
     * @return
     */
    String gerOrder();

    /**
     * 对外发布：表示接收一个反馈
     *
     * @param msg
     */
    void getMsg(String msg);
}
