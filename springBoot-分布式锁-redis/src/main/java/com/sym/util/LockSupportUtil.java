package com.sym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类
 *
 * @Auther: shenym
 * @Date: 2019-04-09 9:41
 */
public class LockSupportUtil {

    /* 保存分布式锁的Key和对应的线程信息 */
    private static Map<String,List<Thread>> threadMap = new ConcurrentHashMap<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(LockSupportUtil.class);

    /**
     * 挂起当前线程
     * @param key 分布式锁key
     * @throws InterruptedException
     */
    public static void park(String key) throws InterruptedException{
        if(StringUtils.isEmpty( key )){
            LOGGER.error("key is null");
            return;
        }
        // 获取当前线程
        Thread t = Thread.currentThread();
        // 把这个线程信息加入到 threadMap 中
        addThread(key,t);
        // 阻塞
        LockSupport.park(null);
        // 如果线程被中断了
        if( t.isInterrupted() ){
            throw new InterruptedException(t.getId()+" is interrupted");
        }
    }

    /**
     * 恢复分布式key对应的挂起线程
     * @param key 分布式锁Key
     * @throws InterruptedException
     */
    public static void unPark(String key){
        if(StringUtils.isEmpty( key )){
            LOGGER.error("key is null");
            return;
        }
        // 获取此key对应的线程集合
        List<Thread> threadList = threadMap.get(key);
        if( threadList == null || threadList.size() <= 0 ){
            LOGGER.warn("key \"{}\" do not correspond any thread",key);
            return;
        }
        // 遍历集合重新唤醒线程
        for( Thread t : threadList ){
            LockSupport.unpark(t);
        }
        threadList = null; //help gc
        threadMap.remove(key);
    }


    /**
     * 将线程t添加到对应key的Map中
     * @param key 分布式锁key
     * @param t 线程
     */
    private static void addThread(String key ,Thread t){
        if( threadMap.containsKey(key) ){
            LOGGER.info("threadMap contains [{}],add thread []",key,t.getId());
            threadMap.get(key).add(t);
        }else{
            List<Thread> list = new LinkedList<>();
            list.add(t);
            threadMap.put(key,list);
        }
    }
}
