package com.sym.service;

/**
 * 定义一些需要操作redis分布式锁{@link com.sym.service.impl.RedisLock}的接口
 *
 * Created by shenym on 2019/9/18.
 */
public interface RedisOperations {

    /**
     * 预缓存脚本信息
     * @param script lua脚本
     * @return redis缓存lua脚本后的sha校验和
     */
    String loadScript(String script);

    /**
     * 执行redis的evalSha命令
     * @param scriptSha lua脚本
     * @param numKeys key的数量
     * @param keysAndArgs key和value的值
     * @return true-获取到锁,false-未获取到锁
     */
    Boolean evalSha(String scriptSha,int numKeys,String...keysAndArgs);


    /**
     * 删除一个缓存键
     * @param key key值
     */
    void del(String key);


    /**
     * redis心跳检测
     * @return true-redis可连可用, false-连接失败
     */
    boolean pong();


    /**
     * 获取锁的存活时间
     * @param key 锁的key
     * @return 剩余存活时间
     */
    long getExpire(String key);

}
