package com.app.thread;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread
 * @version:1.0
 */
public class InterruptTest2 {
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadOne begin sleep for 2000 seconds");
                    Thread.sleep(2000000);
                    System.out.println("threadOne awaking");
                }catch (InterruptedException e) {
                    System.out.println("threadOne is interruptd while sleeping");
                    return;
                }
                System.out.println("threadOne-leaving normally");
            }
        });
        threadOne.start();
        Thread.sleep(1000);
        threadOne.interrupt();
        threadOne.join();
        System.out.println("main thread is over");
    }
}
