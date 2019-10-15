package com.sym.service.impl;

import com.sym.service.RedisOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 与redis交互实现分布式锁的接口{@link RedisOperations}的默认实现类
 *
 * Created by shenym on 2019/9/18.
 */
public class DefaultRedisOperations implements RedisOperations {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultRedisOperations.class);

    private StringRedisTemplate redisTemplate;

    private Charset charset = Charset.forName("utf-8");

    public DefaultRedisOperations(StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(stringRedisTemplate,"stringRedisTemplate不能为空");
        redisTemplate = stringRedisTemplate;
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


    /**
     * redis心跳检测
     * @return true-redis可连可用, false-连接失败
     */
    @Override
    public boolean pong() {
        boolean result = false;
        try {
            String ping = redisTemplate.getConnectionFactory().getConnection().ping();
            LOGGER.info("redis心跳校验：{}",ping);
            result = true;
        }catch (RedisConnectionFailureException e){
            // redis连接失败
            LOGGER.error("无法连接到redis[{}]",e.getMessage());
        }
        return result;
    }


    /**
     * 获取锁的存活时间
     * @param key 锁的key
     * @return 剩余存活时间
     */
    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }
}
