package com.sym;

import com.sym.service.impl.RedisLock;
import com.sym.util.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: shenym
 * @Date: 2019-03-20 15:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {

    @Autowired
    private RedisTemplate stringRedisTemplate;

    /**
     * 加锁方法测试
     */
    @Test
    public void testOne(){
        stringRedisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
         String lock = "if(redis.call('exists',KEYS[1]) == 1) then ";
                lock += "if(redis.call('hget',KEYS[1],KEYS[2]) == ARGV[1]) then ";
                lock += "redis.call('hincrby',KEYS[1],KEYS[3],1) ";
                lock += "redis.call('expire',KEYS[1],60) ";
                lock += "return 1 ";
                lock += "else return 0 end ";
                lock += "else redis.call('hmset',KEYS[1],KEYS[2],ARGV[1],KEYS[3],ARGV[2]) ";
                lock += "redis.call('expire',KEYS[1],60) ";
                lock += "return 1 end";


                long id = Thread.currentThread().getId();
                String threadId = String.valueOf(id);

                Object o = connection.eval(lock.getBytes(), ReturnType.INTEGER, 3, "hash_lock".getBytes(), "threadID".getBytes(), "count".getBytes(),
                        "2".getBytes(), "1".getBytes());
                Integer result = Integer.parseInt(String.valueOf(o));
                if( result == 1 )System.out.println("加锁成功");
                if( result == 0 )System.out.println("加锁失败");
                return false;
            }
        });
    }

    /**
     *
     */
    @Test
    public void testTwo(){
        RedisLock redisLock = new RedisLock("sym_lock");
        boolean b = redisLock.lockWithBlock(60);
        System.out.println(b);
    }


    /**
     * 测试以阻塞的方式获取锁
     */
    @Test
    public void testThree(){
        final CountDownLatch latch = new CountDownLatch(3);
        for( int i=0;i<5;i++ ){
            new Thread(()->{
                try {
                    RedisLock lock = new RedisLock("sym_lock");
                    lock.lockWithBlock();
                    //System.out.println(Thread.currentThread().getName()+",已经重新唤醒...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unLock();
                    }
                }finally {
                    latch.countDown();
                }
            },"线程"+(i+1)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFour(){
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
        redisTemplate.opsForValue().set("good","job",100, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire("good");
        System.out.println(expire);
    }

    @Test
    public void testFive(){
        String script = "if(redis.call('set',KEYS[1],ARGV[1],ARGV[2],ARGV[3],ARGV[4]))then return 1 else return 0 end";
        Boolean execute = (Boolean) stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Long l = connection.eval(script.getBytes(), ReturnType.INTEGER, 1, "mykey".getBytes(), "123".getBytes(), "ex".getBytes(), "60".getBytes(), "nx".getBytes());
            return l > 0;
        });
        System.out.println(execute);
    }

    @Test
    public void main(String[] args) {
        System.out.println(new Boolean(null));
    }

}
