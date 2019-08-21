package com.sym.controller;

import com.sym.service.impl.RedisLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenym on 2019/8/21.
 */
@RestController
public class RedisLockController {


    @RequestMapping("redis/lock")
    public String redisLockTest(){
        RedisLock redisLock = new RedisLock("sym-redis-lock");
        redisLock.lock();
        return "666";
    }

}
