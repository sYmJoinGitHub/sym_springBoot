package com.sym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.LockSupport;

/**
 * redis分布式锁，线程阻塞工具类
 * <p>
 * 如果程序和redis都运行正常，在每个key解锁之后，通过redis发布和订阅，就可以通知到此类unPark()方法，唤醒当前系统阻塞在此key上的线程;
 * 但是如果发生异常，分为两种情况讨论：
 * 1.程序异常：持有锁的程序异常，导致锁在redis不会释放，只能靠过期时间删除，这样就发布不了消息。这时要么监听redis过期key，
 * 要么设置一个定时线程池，由它来定时检测保存在本类threadMap上的key，是否被删除了，一旦发现被删除，就唤醒所有阻塞在此key上的线程
 * 2.redis异常：导致所有阻塞的线程都无法响应，这时只能靠定时线程池，每隔一段时间检测redis心跳，发现redis崩了，就唤醒所有线程
 * <p>
 * Created by shenym on 2019-04-09 9:41.
 */
public class LockSupportUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(LockSupportUtil.class);

    /* 保存分布式锁的Key和对应的线程信息 */
    private static Map<String, Set<Thread>> threadMap = new ConcurrentHashMap<>();

    private static RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");

    /* 定时任务线程池 */
    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);

    /**
     * 挂起当前线程
     *
     * @param key     分布式锁key
     * @param blocker 阻塞对象
     * @throws InterruptedException
     */
    public static void park(String key, Object blocker) throws InterruptedException {
        park(key, null, blocker);
    }


    /**
     * 挂起当前线程
     *
     * @param key      分布式锁key
     * @param waitTime 挂起时长，单位秒
     * @param blocker  阻塞对象
     * @throws InterruptedException
     */
    public static void park(String key, Integer waitTime, Object blocker) throws InterruptedException {
        // 校验key的合法性
        LockSupportUtil.verifyKey(key);
        // 获取当前线程
        Thread t = Thread.currentThread();
        // 把这个线程信息加入到 threadMap 中
        addThread(key, t);
        if (null != waitTime && waitTime > 0) {
            // 再指定时长内，挂起当前线程
            long nanos = waitTime * 1000L * 1000L;
            LOGGER.info("线程 {} 开始阻塞，阻塞时长：{} 秒", t, waitTime);
            LockSupport.parkNanos(blocker, nanos);
        } else {
            // 一直挂起当前线程
            LOGGER.info("线程 [{}] 开始阻塞...", t);
            LockSupport.park(blocker);
        }
        LOGGER.info("thread [{}] 重新唤醒...", t);
        // 如果线程被中断了
        if (t.isInterrupted()) {
            throw new InterruptedException(t.getName() + " is interrupted");
        }
    }

    /**
     * 恢复分布式key对应的挂起线程
     *
     * @param key 分布式锁Key
     * @throws InterruptedException 线程中断异常
     */
    public synchronized static void unPark(String key) {
        if (!threadMap.containsKey(key)) {
            LOGGER.info("threadMap 未保存key={},不唤醒任何线程", key);
            return;
        }
        // 获取此key对应的线程集合
        Set<Thread> threadSet = threadMap.get(key);
        if (threadSet == null || threadSet.size() <= 0) {
            LOGGER.info("此key={}未阻塞任何线程", key);
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
            LOGGER.info("threadMap include [{}],add thread [{}]", key, t.getName());
            threadMap.get(key).add(t);
        } else {
            LOGGER.info("threadMap not include [{}],init and and thread [{}]", key, t.getName());
            Set<Thread> set = new HashSet<>();
            set.add(t);
            threadMap.put(key, set);
        }
    }


    /**
     * 校验key的合法性
     *
     * @param key 分布式锁key 心跳校验
     */
    private static void verifyKey(String key) {
        Assert.notNull(key, "key为空，不合法");
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
        Long expire = redisTemplate.getExpire(key);
        // 如果redis的ttl返回值为-2表示key不存在，-1表示未设置过期时间
        if (expire == -2L) {
            throw new IllegalArgumentException("key=" + key + "不存在");
        } else if (expire == -1L) {
            throw new IllegalArgumentException("key=" + key + "未设置过期时间");
        }
    }


    /**
     * 校验redis是否凑存活
     */
    private void checkAlive(){
        Properties info = redisTemplate.getConnectionFactory().getConnection().info();
    }

}
