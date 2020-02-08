package com.app.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:wuqi
 * @date:2020/2/6
 * @description:com.app.concurrent
 * @version:1.0
 */
public class CyclicBarrierTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args){
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("thread1 step1");
                    cyclicBarrier.await();
                    System.out.println("thread1 step2");
                    cyclicBarrier.await();
                    System.out.println("thread1 step3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.submit(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("thread2 step1");
                    cyclicBarrier.await();
                    System.out.println("thread2 step2");
                    cyclicBarrier.await();
                    System.out.println("thread2 step3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.shutdown();
    }
}
