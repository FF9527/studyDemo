package com.app.concurrent.aqs;

import java.util.concurrent.locks.StampedLock;

/**
 * @author:wuqi
 * @date:2020/1/31
 * @description:com.app.concurrent.aqs
 * @version:1.0
 * @since 1.8
 */
public class StampedLockTest {

    private double x,y;

    private final StampedLock lock = new StampedLock();

    void move(double deltaX, double deltaY){
        //写锁--不重入独占锁
        long stamp = lock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin(){
        //乐观读锁  实际使用时检测锁的状态，提高了效率
        long stamp = lock.tryOptimisticRead();
        double currentX = x;
        double currentY = y;
        //实际操作数据前需要检测锁的状态，stamp改变即被写锁抢占过
        if (!lock.validate(stamp)){
            //悲观读锁重新获取数据
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            }finally {
                lock.unlockRead(stamp);
            }
        }
        //数据线程安全，但可能是快照
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    void moveIfAtOrigin(double newX, double newY){
        //悲观读锁--不重入共享锁
        long stamp = lock.readLock();
        try {
            while (x == 0.0 && y == 0.0){
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L){
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                }else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                }
            }
        }finally {
            lock.unlock(stamp);
        }
    }
}
