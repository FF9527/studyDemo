package com.app.juc.aqs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @author:wuqi
 * @date:2020/1/12
 * @description:com.app.concurrent.aqs
 * @version:1.0
 */
public class AQSTest {

    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int QUEUESIZE = 10;

    static class Prod implements Runnable{
        @Override
        public void run() {
            lock.lock();
            try {
                while (queue.size() == QUEUESIZE){
                    System.out.println("queue full!thread await"+ Thread.currentThread());
                    notEmpty.await();
                }
                queue.add("ele");
                notFull.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    static class Cons implements Runnable{
        @Override
        public void run() {
            lock.lock();
            try {
                while (0 == queue.size()){
                    System.out.println("queue empty!thread await"+ Thread.currentThread());
                    notFull.await();
                }
                String ele = queue.poll();
                notEmpty.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while(true){
            new Thread(new Prod()).start();
            new Thread(new Cons()).start();
        }
    }
}
