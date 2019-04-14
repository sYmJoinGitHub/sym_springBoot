package com.sym.controller;

import com.sym.service.impl.RedisLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: shenym
 * @Date: 2019-03-26 14:51
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    /**
     * 开启5个线程测试分布式锁
     * @return
     */
    @RequestMapping("test")
    public String run(){
        StringBuilder sb = new StringBuilder();
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService threadPool = Executors.newFixedThreadPool(6);
        for( int i=0;i<5;i++ ){
            int idx = i+1;
            threadPool.execute(()->{
                Thread.currentThread().setName("线程"+idx);
                RedisLock redisLock = new RedisLock("hash_lock");
                try {
                    if( redisLock.lock() ){
                        sb.append(Thread.currentThread().getName()).append("获取到锁...<br/>");
                    }else{
                        sb.append(Thread.currentThread().getName()).append("没抢到...<br/>");
                    }
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
