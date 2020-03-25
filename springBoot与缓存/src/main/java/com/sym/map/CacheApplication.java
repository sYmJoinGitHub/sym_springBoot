package com.sym.map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * springBoot缓存知识点的启动类
 *
 * Created by 沈燕明 on 2018/11/15.
 */
// @MapperScan(value = "com.sym.cache") // 当mapper接口很多时，可以使用扫描的方式
@SpringBootApplication
@EnableCaching //开启缓存
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
