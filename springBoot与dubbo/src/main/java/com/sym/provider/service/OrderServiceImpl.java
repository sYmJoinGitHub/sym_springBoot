package com.sym.provider.service;

import com.sym.api.OrderApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author shenyanming
 * Created on 2020/7/21 17:04
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderApi {

    @Override
    public String getOrder() {
        return "您好！订单来了，请尽快处理";
    }

    @Override
    public void setOrder(String order) {
        log.info("收到反馈：{}", order);
    }
}
