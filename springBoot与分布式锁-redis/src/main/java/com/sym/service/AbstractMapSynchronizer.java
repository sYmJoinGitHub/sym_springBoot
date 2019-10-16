package com.sym.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.util.Assert;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 模仿JUC包下的{@link java.util.concurrent.locks.AbstractQueuedSynchronizer},
 * 不过它是通过CLH队列实现, 这边没有那么复杂, 直接使用{@link ConcurrentHashMap}实现...
 *
 * Created by shenym on 2019/10/14.
 */
public abstract class AbstractMapSynchronizer implements ILock {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMapSynchronizer.class);

    // 保存分布式锁的Key和对应的线程集合信息, 定义为成员变量, 让每个实例的数据都可以放到一起（分布式锁, 解锁时要释放所有...）
    private final static Map<String, Set<Thread>> threadMap;

    // 定时任务线程池, 用来与redis交互, 防止程序异常死锁的出现...
    private static ScheduledExecutorService threadPool;

    // 标识是否启动了定时任务, 仅仅会在真正开始使用时才启动定时任务
    private static AtomicBoolean isStarted = new AtomicBoolean();

    // 自定义封装, 用来操作redis的接口类
    protected RedisOperations redisOperations;

    // 当前实例对应的分布式锁的key
    protected String lockKey;

    static {
        threadMap = new ConcurrentHashMap<>();
        threadPool = Executors.newScheduledThreadPool(3);
        /*
         * 在JVM关闭的时候，关掉线程池
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> threadPool.shutdown()));
    }

    public AbstractMapSynchronizer(String lockKey, RedisOperations redisOperations){
        Assert.notNull(lockKey,"lockKey不能为空");
        Assert.notNull(redisOperations,"redisOperations不能为空");
        this.lockKey = lockKey;
        this.redisOperations = redisOperations;
    }

    /**
     * 尝试一次获取锁资源, 方法立即返回
     * @param lockTime 获取到锁时, 对锁的占用时间
     * @return
     */
    protected abstract boolean tryRequire(int lockTime);


    /**
     * 尝试一次解锁, 方法立即返回
     * @return
     */
    protected abstract boolean tryRelease();


    /**
     * 尝试申请锁一次, 此方法会立即返回
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：秒), 必须指定否则容易造成死锁
     * @return true-成功, false-失败
     */
    @Override
    public boolean lock(int lockTime) {
        return tryRequire(lockTime);
    }


    /**
     * 尝试申请锁, 并且在未获取到锁时阻塞; 方法会一直阻塞, 直到被唤醒
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：秒), 必须指定否则容易造成死锁
     */
    @Override
    public void lockAwait(int lockTime) throws InterruptedException {
        startTimeTaskIfNecessary();
        Thread t = Thread.currentThread();
        for(;;){
            /*
             * 解释一下这边的逻辑：
             * 一个自旋的方式, 每次调用先请求tryRequire()获取锁, 能获取到方法返回; 获取不到校验是否可以被阻塞
             * 如果不可以阻塞, 方法报错返回; 如果可以阻塞, 将其加入到Map中, 然后开始阻塞; 如果加入到Map失败,
             * 继续循环, 尝试加入...
             *
             */
             if( tryRequire(lockTime) ){
                 return;
             }else{
                 if( shouldParkByVerifyKey(lockKey) && addToMap(lockKey, t) ){
                     LockSupport.park(this);
                     if( Thread.interrupted() ) throw new InterruptedException();
                     if( !redisOperations.pong() ) throw new RuntimeException("redis宕机, 程序返回");
                 }
             }
        }
    }


    /**
     * 尝试申请锁, 并且未获取到锁时阻塞; 方法会阻塞一段时间, 到点了如果还没有获取到锁便返回
     * @param lockTime 当获取锁时, 线程持有锁的时间(单位：秒), 必须指定否则容易造成死锁
     * @param waitTime 阻塞等待时间
     * @param timeUnit 阻塞等待时间的单位
     */
    @Override
    public void lockAwait(int lockTime, long waitTime, TimeUnit timeUnit) throws InterruptedException {
        startTimeTaskIfNecessary();
        long needTime = timeUnit.toNanos(waitTime); // 用户指定等待的时间数(纳秒)
        long deadTime = needTime + System.nanoTime(); // 当前时间点+用户指定等待的时间数(纳秒), 表示此方法需要返回的最后时间点
        Thread t = Thread.currentThread();
        for(;;){
            if( tryRequire(lockTime) ){
                // 如果可以获取到锁, 直接返回
                return;
            }else{
                needTime = deadTime - System.nanoTime();
                // 如果时间点到了, 方法就不再阻塞了, 直接返回
                if( needTime <= 0L ) return;
                // 等待时间点未到, 将其挂起
                if( shouldParkByVerifyKey(lockKey) && addToMap(lockKey,t)){
                    LockSupport.parkNanos(this, needTime);
                    if (Thread.interrupted()) throw new InterruptedException();
                }
            }
        }
    }


    /**
     * 尝试解锁
     * @return true-解锁，false-解锁失败
     */
    @Override
    public boolean unlock() {
        boolean f = tryRelease();
        if( f ){
            unpark(lockKey);
        }
        return f;
    }


    /**
     * 唤醒分布式锁key, 对应的阻塞线程
     *
     * @param key 分布式锁Key
     */
    public static void unpark(String key) {
        synchronized ( AbstractMapSynchronizer.class ){
            if (!threadMap.containsKey(key)) return;
            // 获取此key对应的线程集合,
            Set<Thread> threadSet = threadMap.get(key);
            if (threadSet == null || threadSet.size() == 0) return;
            // 遍历集合重新唤醒线程
            for (Thread t : threadSet) {
                LockSupport.unpark(t);
            }
            threadSet = null; //help gc
            threadMap.remove(key);
        }
    }


    /**
     * 唤醒所有阻塞的线程
     */
    private void unparkAll(){
        synchronized (AbstractMapSynchronizer.class){
            if( threadMap.isEmpty() ) return;
            for( Map.Entry<String, Set<Thread>> entry : threadMap.entrySet() ){
                Set<Thread> value = entry.getValue();
                for( Thread t : value ){
                    LockSupport.unpark(t);
                }
            }
            threadMap.clear();
        }
    }



    /**
     * 将线程t添加到对应key的Map中
     *
     * @param key 分布式锁key
     * @param t   线程
     */
    private boolean addToMap(String key, Thread t) {
        boolean f = false;
        try {
            synchronized (threadMap){
                if (threadMap.containsKey(key)) {
                    threadMap.get(key).add(t);
                } else {
                    Set<Thread> set = new HashSet<>();
                    set.add(t);
                    threadMap.put(key, set);
                }
                f = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 判断当前线程是否可以被挂起等待, 实际就是校验它申请的分布式锁key
     * 是否在redis上, 并且是否设置了过期时间
     *
     * @param key 分布式锁key 心跳校验
     * @return true-可以被挂起, false-不可以被挂起
     */
    private boolean shouldParkByVerifyKey(String key) {
        long expire = this.redisOperations.getExpire(key);
        // 如果redis的ttl返回值为-2表示key不存在，-1表示未设置过期时间
        if (expire == -2L) {
            throw new IllegalArgumentException("key=" + key + ", 不存在");
        } else if (expire == -1L) {
            throw new IllegalArgumentException("key=" + key + ", 未设置过期时间");
        }
        return true;
    }


    /**
     * 是否需要启动定时任务
     * @return true-需要, false-不需要
     */
    private void startTimeTaskIfNecessary(){
        /*
         * 记录下这边的意思：
         * 第一个判断：!isStarted.get(), 如果已经启动了, 即结果为false, 方法直接结束;
         * 第二个判断：当未启动时(初始化状态), 取非后结果为true, CAS更新成功的线程启动定时任务
         */
        if( !isStarted.get() && isStarted.compareAndSet(false,true)){
            // CAS更新成功的线程来启动定时任务
            this.timedTask();
        }
    }

    /**
     * 周期性任务
     */
    private void timedTask(){

        /*
         * 每隔一分钟校验redis的存活时间, 一旦发现redis挂了, 则唤醒所有线程
         */
        threadPool.scheduleAtFixedRate(()->{
            if( !this.redisOperations.pong() ) {
                LOGGER.warn("redis宕机, 将唤醒所有阻塞线程...");
                unparkAll();
            }
        },1,30, TimeUnit.SECONDS);

        /*
         * 每隔两分钟轮询key的有效性
         */
        threadPool.scheduleAtFixedRate(()->{
            if( threadMap.isEmpty() ) return;
            Iterator<Map.Entry<String, Set<Thread>>> iterator = threadMap.entrySet().iterator();
            while( iterator.hasNext() ){
                Map.Entry<String, Set<Thread>> entry = iterator.next();
                try {
                    long expire = redisOperations.getExpire(entry.getKey());
                    if( expire == -2L ){ // 表示key已被过期删除掉
                        synchronized (AbstractMapSynchronizer.class){
                            Set<Thread> threadSet = entry.getValue();
                            if( threadSet == null || threadSet.isEmpty() ) return;
                            LOGGER.warn("检测到分布式锁[{}]已失效, 将唤醒相应线程集", entry.getKey());
                            // 唤醒此key下的所有线程
                            for (Thread t : threadSet) {
                                LockSupport.unpark(t);
                            }
                            threadSet = null; //help gc
                            iterator.remove();
                        }
                    }
                }catch (RedisConnectionFailureException e){
                    // redis连接失败
                    unparkAll();
                    break; // 退出循环
                }
            }
        },1,1,TimeUnit.MINUTES);
    }

}
