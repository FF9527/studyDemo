package com.app.concurrent.Atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author:wuqi
 * @date:2020/1/6
 * @description:com.app.concurrent.Atomic
 * @version:1.0
 */
public class AtomicTest {

    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrayOne = new Integer[]{0,1,2,3,0,5,6,0,56,0};

    private static Integer[] arrayTwo = new Integer[]{10,1,2,3,0,5,6,0,56,0};

    private static void zeroCount(Integer[] array){
        if (array == null || array.length == 0){
            return;
        }
        int size = array.length;
        for (int i = 0; i< size; ++i){
            if (array[i].intValue() == 0){
                atomicLong.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                zeroCount(arrayOne);
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                zeroCount(arrayTwo);
            }
        });

        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println("zero count :" + atomicLong.get());

    }
}
