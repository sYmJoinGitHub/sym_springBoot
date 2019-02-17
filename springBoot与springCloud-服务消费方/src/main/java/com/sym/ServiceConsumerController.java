package com.sym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * springCloud是使用轻量级的Http请求进行通信的，所以需要使用Controller作为通信的载体
 *
 * Created by 沈燕明 on 2018/12/3.
 */
@RestController
public class ServiceConsumerController {

    /**
     * 使用restTemplate作为交互的工具
     */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("getServiceOrder")
    public String getServiceOrder(){
        return restTemplate.getForObject("http://SERVICE-PROVIDER/getOrder",String.class);
    }

}
