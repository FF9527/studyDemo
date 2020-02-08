package com.app.concurrent.LockSupport;

import org.junit.Test;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author:wuqi
 * @date:2020/1/8
 * @description:com.app.concurrent.LockSupport
 * @version:1.0
 */

public class LockSupportTest {

    /**
     * LockSupport.park():阻塞线程
     * 当前线程已拿到LockSupport关联的许可证时，立刻返回
     * 否则阻塞挂起
     */
    @Test
    public void parkTest(){
        System.out.println("begin park!");
        LockSupport.park();
        System.out.println("end park");
    }

    /**
     * LockSupport.unpark():线程唤醒
     * 参数线程如果没有持有thread与LockSupport的关联许可证，则让线程持有，
     * park()后unpark(thread)可以唤醒park()阻塞的thread--下面unparkTest2()
     * 若unpark后park(),--下面unparkTest()
     */
    @Test
    public void unparkTest(){
        System.out.println("begin park!");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("end park");
    }

    @Test
    public void unparkTest2() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park");
                LockSupport.park();
                System.out.println("child thread unpark");
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread begin unpark");
        LockSupport.unpark(thread);
    }

    /**
     * thread.interrupt()也可以引起park()的返回
     * 所以park()阻塞后返回，不会告诉是何种原因返回，所以需要根据park()原因，检查条件是否满足
     * 即是否符合条件后unpark还是被中断
     * @throws InterruptedException
     */
    @Test
    public void unparkTest3() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park!");
                while(!Thread.currentThread().isInterrupted()){
                    LockSupport.park();
                }
                System.out.println("child thread unpark");
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread begin unpark");
        thread.interrupt();
    }

    public void testPark(){
        LockSupport.park(this);
    }

    /**
     * park(Object blocker)，阻塞时，传入this,可检查哪个类阻塞
     * 当线程唤醒后，会清除blocker，所以一般都是在线程阻塞时才分析原因blocker
     */
    @Test
    public void parkTest1(){
        LockSupportTest test = new LockSupportTest();
        test.testPark();
    }

    /**
     * parkNanos(long nanos)，阻塞nanos纳秒
     */
    @Test
    public void parkNanosTest(){
        System.out.println("begin park!");
        LockSupport.parkNanos(2000000000);//ns
        System.out.println("end park");
    }

    /**
     * parkUntil(long nanos)，阻塞至deadline毫秒
     */
    @Test
    public void parkUntilTest(){
        System.out.println("begin park!");
        LockSupport.parkUntil(this, new Date().getTime() + 10000);//ms
        System.out.println("end park");
    }

}
