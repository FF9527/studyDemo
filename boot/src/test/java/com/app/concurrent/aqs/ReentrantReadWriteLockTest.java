package com.app.concurrent.aqs;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author:wuqi
 * @date:2020/1/25
 * @description:com.app.concurrent.aqs
 * @version:1.0
 */
public class ReentrantReadWriteLockTest {

    private ArrayList<String> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void add(String e){
        writeLock.lock();
        try {
            list.add(e);
        }finally {
            writeLock.unlock();
        }
    }

    public void remove(String e){
        writeLock.lock();
        try {
            list.remove(e);
        }finally {
            writeLock.unlock();
        }
    }

    public String get(int index){
        readLock.lock();
        try {
            return list.get(index);
        }finally {
            readLock.unlock();
        }
    }
}
