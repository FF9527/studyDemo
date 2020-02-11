package com.app.redis.lock;

import com.app.DateUtils;
import com.app.redis.RedisUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:wuqi
 * @date:2020/2/8
 * @description:com.app.redis.lock
 * @version:1.0
 */
public class AppA {
    public static void main(String[] args){
        RedisWithLock lock = new RedisWithLock("lock");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("appA thread1 lock :"+DateUtils.format(System.currentTimeMillis()));
                    Thread.sleep(10000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    System.out.println("appA thread1 unlock :"+DateUtils.format(System.currentTimeMillis()));
                }
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("appA thread2 lock :"+DateUtils.format(System.currentTimeMillis()));
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    System.out.println("appA thread2 unlock :"+DateUtils.format(System.currentTimeMillis()));
                }
            }
        });
        pool.shutdown();
        while (!pool.isTerminated()){

        }
        RedisUtils.close();
    }
}
