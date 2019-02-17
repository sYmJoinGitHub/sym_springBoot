package com.sym.concurrenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by 沈燕明 on 2018/11/15.
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    /**
     * 查询数据，测试@Cacheable注解-缓存数据
     * @param id
     * @return
     */
    @RequestMapping("selectOne/{id}")
    public CacheBean selectOne(@PathVariable Integer id){
        return cacheService.selectOne(id);
    }

    /**
     * 更新数据，测试@CachePut注解-更新缓存
     * @param bean
     * @return
     */
    @GetMapping("update")
    public CacheBean update(CacheBean bean){
        return cacheService.update(bean);
    }
}
