package com.sym.service;
/**
 * 这个接口是服务提供方对外发布的接口，需要将它完全拷贝到本工程(服务消费方)下
 * 且它的类全路径要和它在服务提供方工程的全路径一模一样
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
