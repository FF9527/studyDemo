package com.app.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author:wuqi
 * @date:2020/2/6
 * @description:com.app.concurrent
 * @version:1.0
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread1 over");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread2 over");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        semaphore.acquire(2);
        System.out.println("all child thread over");
        pool.shutdown();
    }
}
