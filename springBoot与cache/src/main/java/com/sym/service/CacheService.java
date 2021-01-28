package com.sym.service;

import com.sym.domain.CacheBean;
import com.sym.repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.sym.config.CacheConfig.CAFFEINE_CACHE_MANAGER;
import static com.sym.config.CacheConfig.GUAVA_CACHE_MANAGER;

/**
 * Spring提供的缓存注解的使用方式：{@link Cacheable}、{@link CachePut}、{@link CacheEvict}、{@link Caching},
 * 其中{@link CacheConfig}是作全局配置使用，它作用在类上，可以指定一些公用的配置，例如：缓存管理器，key生成策略等
 * <p>
 * Created by 沈燕明 on 2018/11/15.
 */
@Slf4j
@Service
@CacheConfig
public class CacheService {

    /**
     * 模拟数据库查询
     */
    private final CacheRepository cacheRepository;

    public CacheService(CacheRepository cacheMapper) {
        this.cacheRepository = cacheMapper;
    }

    /**
     * {@link Cacheable}可以缓存方法的返回值，方法调用之前也查看缓存中是否有记录:
     * 如果已经有缓存，则不会调用方法，直接返回缓存；
     * 若没有记录，才会执行方法，并将方法返回值保存到缓存中
     */
    @Cacheable(cacheManager = GUAVA_CACHE_MANAGER, cacheNames = "service", key = "#root.target + ':' + #id")
    public CacheBean selectOne(Integer id) {
        log.info("业务查询, id={}", id);
        return cacheRepository.selectOne(id);
    }

    @Cacheable(cacheManager = CAFFEINE_CACHE_MANAGER, cacheNames = "service", key = "#ids.hashCode()")
    public List<CacheBean> selectList(Collection<Integer> ids) {
        log.info("业务查询, ids={}", ids);
        return ids.stream().map(cacheRepository::selectOne).collect(Collectors.toList());
    }

    /**
     * {@link CachePut}, 用于更新缓存, 会直接执行方法再重新赋值缓存. 相比较于{@link Cacheable}它永远都会执行方法,
     * 同时它的缓存键生成策略要与{@link Cacheable}一致, 否则缓存对应不起来, 失去了缓存的意义
     */
    @CachePut(cacheManager = GUAVA_CACHE_MANAGER, cacheNames = "springBoot",
            key = "#root.target + ':' + #result.id", unless = "#result==null")
    public CacheBean update(CacheBean bean) {
        log.info("更新缓存, id={}", bean.getId());
        cacheRepository.update(bean);
        return bean;
    }

    /**
     * {@link CacheEvict}清空缓存，它可以删除指定key的缓存，也可以将指定Cache组件内的所有缓存都清空。
     * beforeInvocation 指定在方法调用之前还是之后清空缓存，多出这项的原因之一：是怕当方法出现异常之后，缓存没有清空...
     */
    @CacheEvict(value = {"springBoot"}, condition = "#result==true")
    public boolean delete(Integer id) {
        log.info("删除bean, id={}", id);
        cacheRepository.delete(id);
        return true;
    }


    /**
     * {@link Caching}注解是一个组合注解，它包含{@link Cacheable}、@{@link CachePut}和
     * {@link CacheEvict} 三个注解用来组合复杂的缓存逻辑。
     */
    @Caching(
            cacheable = {@Cacheable(value = "springBoot", key = "#name")},
            evict = {@CacheEvict(value = "springBoot", key = "#result.name")},
            put = {@CachePut(value = "springBoot", key = "#name")})
    public CacheBean selectByName(String name) {
        return new CacheBean(6, "赵六");
    }
}
