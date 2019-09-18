package com.sym;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by shenym on 2019/9/6.
 */
public class CommonTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("开始..."+Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println("结束..."+Thread.currentThread().isInterrupted());
        });
        t1.start();
        Thread.sleep(5000);
        t1.interrupt();
        //LockSupport.unpark(t1);
    }
}
