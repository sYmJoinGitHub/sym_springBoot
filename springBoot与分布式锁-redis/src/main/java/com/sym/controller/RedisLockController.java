package com.sym.controller;

import com.sym.service.RedisOperations;
import com.sym.service.impl.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenym on 2019/8/21.
 */
@RestController
public class RedisLockController {

    @Autowired
    private RedisOperations redisOperations;

    @RequestMapping("redis/lock")
    public String redisLockTest(){
        RedisLock redisLock = new RedisLock("sym-redis-lock",redisOperations);
        redisLock.lock(60);
        return "666";
    }

}
