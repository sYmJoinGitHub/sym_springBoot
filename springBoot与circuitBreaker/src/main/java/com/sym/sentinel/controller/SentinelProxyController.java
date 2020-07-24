package com.sym.sentinel.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sym.sentinel.config.SentinelConfig;
import com.sym.sentinel.constants.SentinelResourceNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenyanming
 * Created on 2020/7/23 14:27
 */
@RestController
@RequestMapping("/sentinel")
@Slf4j
public class SentinelProxyController {

    private final static String DEFAULT_RETURN_VALUE = "sentinel~";
    private final static String FALL_BACK_RETURN_VALUE = "fallback~";

    /**
     * sentinel限流配置
     * @see SentinelConfig
     */
    @GetMapping("getOneV1/{v}")
    @SentinelResource(value = SentinelResourceNameConstants.SENTINEL_PROXY_CONTROLLER_GET_ONE_V1,
            entryType = EntryType.IN, fallback = "getOneV1Fallback")
    public String getOneV1(@PathVariable("v") int v) throws InterruptedException {
        Thread.sleep(v * 1000);
        return DEFAULT_RETURN_VALUE;
    }

    public String getOneV1Fallback(Throwable throwable){
        log.error("出现异常, ", throwable);
        return FALL_BACK_RETURN_VALUE;
    }
}
