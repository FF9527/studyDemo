package com.app.concurrent;

import com.app.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:wuqi
 * @date:2020/2/5
 * @description:com.app.concurrent
 * @version:1.0
 */
public class SchedulePoolTest {

    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(DateUtils.format(System.currentTimeMillis()));
                }finally {
                    lock.unlock();
                }
            }
        };
        System.out.println(DateUtils.format(System.currentTimeMillis()));
        //pool.schedule(task,5,TimeUnit.SECONDS);
        //pool.scheduleAtFixedRate(task,5,1,TimeUnit.SECONDS);
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(DateUtils.format(System.currentTimeMillis()));
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        };
        pool.scheduleWithFixedDelay(task1,5,1,TimeUnit.SECONDS);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();

    }

}
