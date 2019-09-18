package com.sym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程阻塞工具类
 *
 * 目前有个问题，如果一个程序获取分布式锁成功后，程序死掉了，即没有在主动解锁。
 * 虽然此时加锁的Key可以根据过期时间自己被删除，但是在此之前正在阻塞的程序，
 * 并没有接收到释放锁的信息（它无法判断加锁key到底在不在），会一直永远阻塞下去。
 *
 * 这边目前有2个解决方案：1.监听redis每一个过期的key
 *                     2.本地实现一个定时器，时长就是加锁key的过期时间，时间一到不管加锁key是否被占用，线程都重新唤醒，尝试获取锁
 *
 * @Auther: shenym
 * @Date: 2019-04-09 9:41
 */
public class LockSupportUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(LockSupportUtil.class);

    /* 保存分布式锁的Key和对应的线程信息 */
    private static Map<String, Set<Thread>> threadMap = new ConcurrentHashMap<>();

    /*  */
    private static Lock lock = new ReentrantLock();


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
        LOGGER.info("thread [{}] 开始阻塞...",t.getName());
        LockSupport.park(null);
        LOGGER.info("thread [{}] 重新唤醒...",t.getName());
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
        try{
            lock.lock();
            if (!threadMap.containsKey(key)) {
                LOGGER.info("threadMap 未保存key={},不唤醒任何线程",key);
                return;
            }
        }finally {
            lock.unlock();
        }
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
        try{
            lock.lock();
            if (threadMap.containsKey(key)) {
                LOGGER.info("threadMap include [{}],add thread [{}]", key, t.getName());
                threadMap.get(key).add(t);
            } else {
                LOGGER.info("threadMap not include [{}],init and and thread [{}]", key,t.getName());
                Set<Thread> set = new HashSet<>();
                set.add(t);
                threadMap.put(key, set);
            }
        }finally {
            lock.unlock();
        }
    }


    /**
     * 校验key的合法性
     *
     * @param key
     */
    private static void verifyKey(String key) {
        Assert.notNull(key, "key为空，不合法");
        RedisTemplate redisTemplate = (RedisTemplate)SpringContextUtil.getBean("redisTemplate");
        Long expire = redisTemplate.getExpire(key);
        // 如果redis的ttl返回值为-2表示key不存在，-1表示未设置过期时间
        if (expire == -2L) {
            throw new IllegalArgumentException("key=" + key + "不存在");
        } else if (expire == -1L) {
            throw new IllegalArgumentException("key=" + key + "未设置过期时间");
        }
    }


}
