package com.sym.redis.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
/**
 * dubbo服务提供方，对外发布接口的实现类
 *
 * Created by 沈燕明 on 2018/12/2.
 */
@Component
@Service //@Service是dubbo提供，表示该类作为一个对外服务的接口
public class OrderServiceImpl implements OrderService {
    /**
     * 对外发布：表示获取一个订单
     * @return
     */
    @Override
    public String getOrder() {
        return "您好！订单来了，请尽快处理";
    }

    /**
     * 对外发布：表示接收一个反馈
     * @param order
     */
    @Override
    public void setOrder(String order) {
    }
}
