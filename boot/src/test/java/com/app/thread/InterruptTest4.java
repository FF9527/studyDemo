package com.app.thread;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread
 * @version:1.0
 */
public class InterruptTest4 {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().interrupted()){

                }
                System.out.println("threadOne isInterrupted:" + Thread.currentThread().isInterrupted());
            }
        });
        threadOne.start();
        threadOne.interrupt();
        threadOne.join();
        System.out.println("main thread is over");
    }
}
