package com.sym.hystrix.controler;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sym.constants.HystrixCommandPropertiesConstants.CircuitBreaker.CIRCUIT_BREAKER_ENABLED;
import static com.sym.constants.HystrixCommandPropertiesConstants.CircuitBreaker.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE;
import static com.sym.constants.HystrixCommandPropertiesConstants.CircuitBreaker.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD;
import static com.sym.constants.HystrixCommandPropertiesConstants.CircuitBreaker.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadIsolation.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadIsolation.EXECUTION_ISOLATION_STRATEGY;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadIsolation.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadIsolation.EXECUTION_TIMEOUT_ENABLED;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadPool.CORE_SIZE;
import static com.sym.constants.HystrixCommandPropertiesConstants.ThreadPool.MAX_QUEUE_SIZE;

/**
 * Hystrix限流、降级和熔断的配置.
 * 注意, 如果多个方法拥有相同的配置属性, 可以在类上加上{@link DefaultProperties}指定默认配置.
 *
 * @author shenyanming
 * Created on 2020/7/20 15:47
 */
@RestController
@RequestMapping("/hystrix")
@Slf4j
public class HystrixProxyController {

    private final static String FALLBACK_RETURN_VALUE = "降级了~";
    private final static String LIMIT_RETURN_VALUE = "限流了~";
    private final static String CIRCUIT_BREAKER_RETURN_VALUE = "熔断了~";
    private final static String RETURN_VALUE = "666";

    /**
     * 接口限流
     *
     * @param value 模拟复杂请求, 让当次请求睡眠一定时间, 单位秒
     */
    @GetMapping("/1/{v}")
    @HystrixCommand(fallbackMethod = "getOneV1Fallback", commandProperties = {
            @HystrixProperty(name = EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"),
            @HystrixProperty(name = EXECUTION_TIMEOUT_ENABLED, value = "false"),
            @HystrixProperty(name = EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "1")
    })
    public String getOneV1(@PathVariable("v") int value) throws InterruptedException {
        Thread.sleep(value * 1000);
        log.info("当前线程: {}", Thread.currentThread().getName());
        return RETURN_VALUE;
    }

    public String getOneV1Fallback(int value, Throwable throwable) {
        log.info("接口限流, ", throwable);
        return LIMIT_RETURN_VALUE;
    }

    /**
     * 超时处理
     *
     * @param value 模拟复杂请求, 让当次请求睡眠一定时间, 单位秒
     */
    @GetMapping("/2/{v}")
    @HystrixCommand(fallbackMethod = "getOne2Fallback", commandProperties = {
            @HystrixProperty(name = EXECUTION_ISOLATION_STRATEGY, value = "THREAD"),
            @HystrixProperty(name = EXECUTION_TIMEOUT_ENABLED, value = "true"),
            @HystrixProperty(name = EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "5000"),
    }, threadPoolProperties = {
            @HystrixProperty(name = CORE_SIZE, value = "1"),
            @HystrixProperty(name = MAX_QUEUE_SIZE, value = "1")
    })
    public String getOne2(@PathVariable("v") int value) throws InterruptedException {
        Thread.sleep(value * 1000);
        log.info("当前线程: {}", Thread.currentThread().getName());
        return RETURN_VALUE;
    }

    public String getOne2Fallback(int value, Throwable throwable) {
        log.info("接口降级, ", throwable);
        return FALLBACK_RETURN_VALUE;
    }

    /**
     * 熔断处理
     */
    @GetMapping("/3")
    @HystrixCommand(fallbackMethod = "getOne3Fallback", commandProperties = {
            @HystrixProperty(name = EXECUTION_ISOLATION_STRATEGY, value = "THREAD"),
            @HystrixProperty(name = CIRCUIT_BREAKER_ENABLED, value = "true"),
            @HystrixProperty(name = CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "2"),
            @HystrixProperty(name = CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "2000"),
            @HystrixProperty(name = CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
    }, threadPoolProperties = {
            @HystrixProperty(name = CORE_SIZE, value = "1"),
            @HystrixProperty(name = MAX_QUEUE_SIZE, value = "-1")
    })
    public String getOne3() {
        log.info("当前线程: {}", Thread.currentThread().getName());
        int i = 1 / 0;
        return RETURN_VALUE;
    }

    public String getOne3Fallback(Throwable throwable) {
        log.info("接口熔断, ", throwable);
        return CIRCUIT_BREAKER_RETURN_VALUE;
    }
}
