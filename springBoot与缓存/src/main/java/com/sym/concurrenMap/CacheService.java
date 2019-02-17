package com.sym.concurrenMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * Created by 沈燕明 on 2018/11/15.
 */
@Service
@CacheConfig() // @CacheConfig是作全局配置使用，它作用在类上，可以指定一些公用的配置，例如：缓存管理器，key生成策略等
public class CacheService {

    @Autowired
    private CacheMapper cacheMapper;

    /**
     * @Cacheable 可以缓存方法的返回值，方法调用之前也查看缓存中是否有记录，
     * 如果已经有缓存，则不会调用方法，直接返回缓存；若没有记录，才会执行方法，并将方法返回值保存到缓存中
     */
    @Cacheable(cacheNames = {"springBoot"}, keyGenerator = "mykeyGenerator", condition = "#p0>1", unless = "#result.id>10")
    public CacheBean selectOne(Integer id) {
        System.out.println("查询信息：id=" + id);
        return cacheMapper.selectOne(id);
    }


    /**
     * @CachePut 也是缓存方法的返回值，但是它相当于更新缓存。所以它的流程是直接执行方法，然后重新赋值缓存。
     * 它的key生成策略要和 @Cacheable一样，否则缓存永远更新不起来，因为它们key不一样，都是各干各的
     */
    @CachePut(cacheNames = "springBoot", key = "#result.id", unless = "#result==null")
    public CacheBean update(CacheBean bean) {
        System.out.println("更新缓存,id=" + bean.getId());
        cacheMapper.update(bean);
        return bean;
    }

    /**
     * @CacheEvict 清空缓存，它可以删除指定key的缓存，也可以将指定Cache组件内的所有缓存都清空。
     * beforeInvocation 指定在方法调用之前还是之后清空缓存，多出这项的原因之一：是怕当方法出现异常之后，缓存没有清空...
     */
    @CacheEvict(value = {"springBoot"}, condition = "#result==true")
    public boolean delete(Integer id) {
        System.out.println("删除CacheBean，id=" + id);
        cacheMapper.delete(id);
        return true;
    }


    /**
     * @Caching 注解是一个组合注解，它包含了 @Cacheable、@CachePut和@CacheEvict三个注解
     * 用来组合复杂的缓存逻辑。
     */
    @Caching(
            cacheable = {@Cacheable(value = "springBoot", key = "#name")},
            evict = {@CacheEvict(value = "springBoot", key = "#result.name")},
            put = {@CachePut(value = "springBoot", key = "#name")})
    public CacheBean selectByName(String name) {
        System.out.println("假装查询到数据了,name=" + name);
        return new CacheBean(6, "赵六");
    }
}
