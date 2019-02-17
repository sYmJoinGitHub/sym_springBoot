package com.sym.controller;

import com.sym.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springCloud是使用轻量级的Http请求进行通信的，所以需要使用Controller作为通信的载体
 * 只要在application.yml中配置好，就可以将此应用注册到eureka中
 *
 * Created by 沈燕明 on 2018/12/3.
 */
@RestController
public class ServiceProviderController {

    @Autowired
    private OrderService orderService;

    /**
     * 向外面应用传递数据
     * @return
     */
    @RequestMapping("getOrder")
    public String getOrder(){
        return orderService.getOrder();
    }

    /**
     * 从外面应用接收数据
     * @param order
     */
    @RequestMapping("receiveOrder")
    public void receiveOrder(String order){
        orderService.receiveOrder(order);
    }
}
