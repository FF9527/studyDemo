package com.app.thread;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread
 * @version:1.0
 * interrupt 用于线程协作，通信
 */
public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread() + " hello");
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread interrupt thread");
        thread.interrupt();

        thread.join();
        System.out.println("main is over");
    }
}
