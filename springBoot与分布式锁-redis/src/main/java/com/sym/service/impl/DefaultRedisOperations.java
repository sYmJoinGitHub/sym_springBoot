package com.sym.service.impl;

import com.sym.service.RedisOperations;
import com.sym.util.SpringContextUtil;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 默认的实现类
 *
 * Created by shenym on 2019/9/18.
 */
public class DefaultRedisOperations implements RedisOperations {

    private StringRedisTemplate redisTemplate;

    private Charset charset = Charset.forName("utf-8");

    public DefaultRedisOperations() {
        redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
    }

    /**
     * 缓存脚本
     *
     * @param script lua脚本
     * @return lua脚本的校验和
     */
    @Override
    public String loadScript(String script) {
        Assert.hasLength(script, "脚本不能为空");
        return redisTemplate.execute((RedisCallback<String>) conn -> conn.scriptLoad(script.getBytes(charset)));
    }

    /**
     * 执行lua脚本(使用校验和执行)
     *
     * @param scriptSha   lua脚本
     * @param numKeys     key的数量
     * @param keysAndArgs key和value的值
     * @return
     */
    @Override
    public Boolean evalSha(String scriptSha, int numKeys, String... keysAndArgs) {
        Assert.notNull(keysAndArgs, "参数不能为空");
        byte[][] params = new byte[keysAndArgs.length][];
        for (int i = 0, len = keysAndArgs.length; i < len; i++) {
            params[i] = keysAndArgs[i].getBytes(charset);
        }
        return redisTemplate.execute((RedisCallback<Boolean>) conn -> {
            Long o = conn.evalSha(scriptSha, ReturnType.INTEGER, 3, params);
            return o == 1L;
        });
    }

    /**
     * 删除一个key
     *
     * @param key key值
     */
    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }
}
