package com.app.concurrent;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.concurrent
 * @version:1.0
 */
public class OpNewSort {

    public static class ReadThread extends Thread{
        public void run(){
            while (!Thread.currentThread().isInterrupted()){
                if (ready){
                    System.out.println(num + num);
                }
                System.out.println("read thread ......");
            }
        }
    }

    public static class WriteThread extends Thread{
        public void run(){
            num = 2;//
            ready = true;//此处两行代码可能会指令重排，导致上面输出结果可能为0，但是我执行的结果好像一直是4
            System.out.println("writeThread set over ...");
        }
    }

    private static int num = 0;
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        ReadThread rt = new ReadThread();
        rt.start();
        WriteThread wt = new WriteThread();
        wt.start();
        Thread.sleep(1000);
        rt.interrupt();
        System.out.println("main exit");
    }
}
