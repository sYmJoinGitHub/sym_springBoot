package com.sym.config.guava;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author shenyanming
 * Created on 2021/1/28 13:57
 */
public class GuavaCacheManager extends AbstractCacheManager {

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptyList();
    }

    @Override
    protected Cache getMissingCache(String name) {
        return new GuavaCache(name);
    }

    @Override
    public Cache getCache(String name) {
        return super.getCache(name);
    }
}
