package com.sym.redis.service;
/**
 * dubbo服务提供方，对外发布的接口
 *
 * @author shenym
 * @date 2018/12/2
 */
public interface OrderService {
    /**
     * 对外发布：表示获取一个订单
     * @return 订单名称
     */
    String getOrder();

    /**
     * 对外发布：表示接收一个反馈
     * @param order
     */
    void setOrder(String order);
}
