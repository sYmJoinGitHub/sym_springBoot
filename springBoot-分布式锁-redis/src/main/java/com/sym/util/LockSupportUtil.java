package com.sym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类
 *
 * @Auther: shenym
 * @Date: 2019-04-09 9:41
 */
public class LockSupportUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(LockSupportUtil.class);

    /* 保存分布式锁的Key和对应的线程信息 */
    private static Map<String, Set<Thread>> threadMap = new ConcurrentHashMap<>();


    /**
     * 挂起当前线程
     *
     * @param key 分布式锁key
     * @throws InterruptedException
     */
    public static void park(String key) throws InterruptedException {
        // 校验key的合法性
        LockSupportUtil.verifyKey(key);
        // 获取当前线程
        Thread t = Thread.currentThread();
        // 把这个线程信息加入到 threadMap 中
        addThread(key, t);
        // 阻塞
        LockSupport.park(null);
        // 如果线程被中断了
        if (t.isInterrupted()) {
            throw new InterruptedException(t.getId() + " is interrupted");
        }
    }

    /**
     * 恢复分布式key对应的挂起线程
     *
     * @param key 分布式锁Key
     * @throws InterruptedException
     */
    public static void unPark(String key) {
        if (!threadMap.containsKey(key)) return;
        // 获取此key对应的线程集合
        Set<Thread> threadSet = threadMap.get(key);
        if (threadSet == null || threadSet.size() <= 0) {
            LOGGER.warn("key \"{}\" do not correspond any thread", key);
            return;
        }
        // 遍历集合重新唤醒线程
        for (Thread t : threadSet) {
            LockSupport.unpark(t);
        }
        threadSet = null; //help gc
        threadMap.remove(key);
    }


    /**
     * 将线程t添加到对应key的Map中
     *
     * @param key 分布式锁key
     * @param t   线程
     */
    private static void addThread(String key, Thread t) {
        if (threadMap.containsKey(key)) {
            LOGGER.info("threadMap contains [{}],add thread [{}]", key, t.getId());
            threadMap.get(key).add(t);
        } else {
            Set<Thread> set = new HashSet<>();
            set.add(t);
            threadMap.put(key, set);
        }
    }


    /**
     * 校验key的合法性
     *
     * @param key
     */
    private static void verifyKey(String key) {
        Assert.notNull(key, "key为空，不合法");
        RedisTemplate redisTemplate = SpringContextUtil.getBean(RedisTemplate.class);
        Long expire = redisTemplate.getExpire(key);
        // 如果redis的ttl返回值为-2表示key不存在，-1表示未设置过期时间
        if (expire == -2L) {
            throw new IllegalArgumentException("key=" + key + "不存在");
        } else if (expire == -1L) {
            throw new IllegalArgumentException("key=" + key + "未设置过期时间");
        }
    }

}
