package com.app.concurrent;

import sun.misc.Unsafe;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.concurrent
 * @version:1.0
 */
public class UnsafeTest {
    static final Unsafe unsafe = Unsafe.getUnsafe();//必须BootstrapClassLoader 才能实例化
    static final long stateOffset;
    private volatile long state = 0;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args){
        UnsafeTest test = new UnsafeTest();
        Boolean sucess = unsafe.compareAndSwapInt(test,stateOffset,0,1);
        System.out.println(sucess);
    }
}
