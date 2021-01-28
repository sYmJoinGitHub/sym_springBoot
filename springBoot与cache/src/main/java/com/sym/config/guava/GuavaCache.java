package com.sym.config.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author shenyanming
 * Created on 2021/1/28 13:43
 */
public class GuavaCache extends AbstractValueAdaptingCache {

    private final String name;
    private Cache<Object, Object> cache;

    public GuavaCache() {
        this("default");
    }

    public GuavaCache(String name) {
        super(true);
        this.name = name;
        this.cache = CacheBuilder.newBuilder().build();
    }

    @Override
    protected Object lookup(Object key) {
        return cache.getIfPresent(key);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            return (T) cache.get(key, valueLoader);
        } catch (ExecutionException e) {
            // TODO exception defined
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        cache.put(key, toStoreValue(value));
    }

    @Override
    public void evict(Object key) {
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        cache.cleanUp();
    }
}
