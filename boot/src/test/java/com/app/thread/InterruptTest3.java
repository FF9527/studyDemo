package com.app.thread;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread
 * @version:1.0
 */
public class InterruptTest3 {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){

                }
            }
        });
        threadOne.start();
        threadOne.interrupt();//设置中断标志
        System.out.println("isInterrupted:" + threadOne.isInterrupted());//检测threadOne线程是否被中断
        System.out.println("isInterrupted:" + threadOne.interrupted());//interrupted是static方法currentThread.interrupted(),如果true清除中断标志
        System.out.println("isInterrupted:" + Thread.interrupted());//interrupted是static方法currentThread.interrupted()
        System.out.println("isInterrupted:" + threadOne.isInterrupted());//检测threadOne线程是否被中断
        threadOne.join();
        System.out.println("main thread is over");
    }
}
