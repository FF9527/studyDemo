package com.app.concurrent.LockSupport;

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
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<>();

    public void lock(){
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        while (waiters.peek() != current || !locked.compareAndSet(false,true)){
            LockSupport.park(this);
            if (Thread.interrupted()){
                wasInterrupted = true;
            }
        }
        waiters.remove();
        if (wasInterrupted){
            current.interrupt();
        }
    }

    public void unlock(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
