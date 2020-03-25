package com.sym;

import com.sym.map.domain.CacheBean;
import com.sym.map.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author shenym
 * @date 2020/3/25 10:53
 */
@SpringBootTest
public class MapCacheTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void selectTest(){
        CacheBean cacheBean = cacheService.selectOne(1);
        System.out.println(cacheBean);
    }

    @Test
    public void updateTest(){
        cacheService.update(new CacheBean(1, "update"));
    }
}
