package com.app.juc.aqs;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:wuqi
 * @date:2020/1/25
 * @description:com.app.concurrent.aqs
 * @version:1.0
 */
public class ReentrantLockTest {

    private ArrayList<String> list = new ArrayList<>();
    private volatile ReentrantLock lock = new ReentrantLock();

    public void add(String e){
        lock.lock();
        try {
            list.add(e);
        }finally {
            lock.unlock();
        }
    }

    public void remove(String e){
        lock.lock();
        try {
            list.remove(e);
        }finally {
            lock.unlock();
        }
    }

    public String get(int index){
        lock.lock();
        try {
            return list.get(index);
        }finally {
            lock.unlock();
        }
    }
}
