package com.sym.api;

/**
 * dubbo接口
 *
 * @author shenym
 * @date 2018/12/2
 */
public interface OrderApi {
    /**
     * 对外发布：表示获取一个订单
     *
     * @return 订单名称
     */
    String getOrder();

    /**
     * 对外发布：表示接收一个反馈
     *
     * @param order
     */
    void setOrder(String order);
}
