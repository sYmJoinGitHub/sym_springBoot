package com.sym;

import com.sym.domain.CacheBean;
import com.sym.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author shenym
 * @date 2020/3/25 10:53
 */
@Slf4j
@SpringBootTest(classes = CacheApplication.class)
public class CacheTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void selectTest(){
        CacheBean cacheBean = cacheService.selectOne(1);
        log.info("查询第一次：{}", cacheBean);
        CacheBean bean = cacheService.selectOne(1);
        log.info("查询第二次：{}", bean);
    }

    @Test
    public void selectListTest(){
        List<CacheBean> beans = cacheService.selectList(Arrays.asList(110, 119));
        log.info("查询第一次：{}", beans);
        beans = cacheService.selectList(Arrays.asList(110, 119));
        log.info("查询第一次：{}", beans);
    }

    @Test
    public void updateTest(){
        CacheBean bean = cacheService.update(new CacheBean(1, "update"));
        log.info("更新缓存, bean={}", bean);
        CacheBean cacheBean = cacheService.selectOne(1);
        log.info("更新查询, bean={}", cacheBean);
    }
}
