package com.sym.hystrix;

import com.sym.hystrix.annotation.EnableHystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * Hystrix有两种模式的隔离策略：线程池隔离、信号量隔离.
 * -- 线程池隔离
 * 使用一个线程池来存储当前的请求，线程池对请求作处理，设置任务返回处理超时时间，堆积的请求堆积入线程池队列.
 * 这种方式需要为每个依赖的服务申请线程池，有一定的资源消耗，好处是可以应对突发流量（流量洪峰来临时，处理不完
 * 可将数据存储到线程池队里慢慢处理）
 *
 * -- 信号量隔离
 * 使用一个信号量来记录当前有多少个线程在运行，请求来先判断计数器的数值，若超过设置的最大线程个数则丢弃改类型的新请求，
 * 若不超过则执行计数操作请求来计数器+1，请求返回计数器-1. 这种方式是严格的控制线程且立即返回模式，无法应对突发流量
 * （流量洪峰来临时，处理的线程超过数量，其他的请求会直接返回，不继续去请求依赖的服务）
 *
 * @author shenyanming
 * Created on 2020/7/20 15:46
 */
@SpringBootApplication
@EnableHystrix
@Profile("hystrix")
@Slf4j
public class HystrixApplication{
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class);
        log.info("服务启动成功");
    }
}
