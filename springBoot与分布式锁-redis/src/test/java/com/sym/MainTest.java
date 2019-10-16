package com.sym;

import com.sym.service.impl.DefaultRedisOperations;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试脚本, 直接加锁
     */
    @Test
    public void testOne() {
        stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            String lock = "if(redis.call('exists',KEYS[1]) == 1) then ";
            lock += "if(redis.call('hget',KEYS[1],KEYS[2]) == ARGV[1]) then ";
            lock += "redis.call('hincrby',KEYS[1],KEYS[3],1) ";
            lock += "redis.call('expire',KEYS[1],60) ";
            lock += "return 1 ";
            lock += "else return 0 end ";
            lock += "else redis.call('hmset',KEYS[1],KEYS[2],ARGV[1],KEYS[3],ARGV[2]) ";
            lock += "redis.call('expire',KEYS[1],60) ";
            lock += "return 1 end";
            Object o = connection.eval(lock.getBytes(), ReturnType.INTEGER, 3, "hash_lock".getBytes(), "threadID".getBytes(), "count".getBytes(),
                    "2".getBytes(), "1".getBytes());
            int result = Integer.parseInt(String.valueOf(o));
            if (result == 1) System.out.println("加锁成功");
            if (result == 0) System.out.println("加锁失败");
            return false;
        });
    }

    /**
     * 测试分布式锁的 lock() 方法, 此方法会立即返回结果
     */
    @Test
    public void testTwo() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        for( int i=0; i<3 ;i++ ){
            new Thread(()->{
                RedisLock redisLock = new RedisLock("sym",new DefaultRedisOperations(stringRedisTemplate));
                boolean b = redisLock.lock(10);
                System.out.println(Thread.currentThread()+", 夺锁结果："+b);
                latch.countDown();// 这边只是为了测试, 实际运用要放到finally块中
            }).start();
        }
        latch.await();
    }



    /**
     * 测试分布式锁的 lockAwait() 方法, 此方法会一直阻塞
     */
    @Test
    public void testThree() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    RedisLock lock = new RedisLock("sym_lock");
                    lock.lockAwait(60);
                    System.out.println(Thread.currentThread().getName()+",已经重新唤醒...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程" + (i + 1)).start();
        }
        latch.await();
    }


    /**
     * 测试分布式锁的 lockAwait() 方法, 此方法可以超时等待
     */
    @Test
    public void testFour() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    RedisLock lock = new RedisLock("sym_lock");
                    lock.lockAwait(60, 30, TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName()+",已经重新唤醒...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程" + (i + 1)).start();
        }
        latch.await();
    }


    /**
     * redis脚本测试
     */
    @Test
    public void testFive() {
        String script = "if(redis.call('set',KEYS[1],ARGV[1],ARGV[2],ARGV[3],ARGV[4]))then return 1 else return 0 end";
        Boolean execute = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Long l = connection.eval(script.getBytes(), ReturnType.INTEGER, 1, "mykey".getBytes(), "123".getBytes(), "ex".getBytes(), "60".getBytes(), "nx".getBytes());
            return l > 0;
        });
        System.out.println(execute);
    }


    /**
     * 实际环境测试
     * @throws InterruptedException
     */
    @Test
    public void testSix() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);


        // 开启两个线程抢夺分布式锁，然后将redis关闭（模拟redis崩了）或者 key删除（模拟抢到锁的程序崩了）
        // 查看被阻塞的线程能否被重新唤醒
        new Thread(() -> {
            try {
                RedisLock redisLock = new RedisLock("sym_lock_123");
                redisLock.lockAwait(2000);
                System.out.println("重新唤醒...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                RedisLock redisLock = new RedisLock("sym_lock_123");
                redisLock.lockAwait(2000);
                System.out.println("重新唤醒...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        latch.await();
    }


    /**
     * 正常情况下测试
     */
    @Test
    public void seven() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        for( int i=0; i<3; i++ ){
            new Thread(()->{
                RedisLock redisLock = new RedisLock("sym");
                try {
                    redisLock.lockAwait(50);
                    System.out.println(Thread.currentThread().getName()+", 获得锁");
                    Thread.sleep(2000);
                    redisLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"线程"+(i+1)).start();
        }

        latch.await();
    }
}
