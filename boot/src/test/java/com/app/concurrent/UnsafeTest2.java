package com.app.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.concurrent
 * @version:1.0
 */
public class UnsafeTest2 {
    static final Unsafe unsafe;

    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args){
        UnsafeTest2 test = new UnsafeTest2();
        Boolean sucess = unsafe.compareAndSwapInt(test,stateOffset,0,1);
        System.out.println(sucess);
    }
}
