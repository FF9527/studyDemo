package com.app.juc;

import sun.misc.Contended;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.concurrent
 * @version:1.0
 * 伪共享:一次只能一个线程写入cache行，若cache行中包含多个变量，多线程操作时只能一个一个操作，不能并发
 */
public class CacheTest {

    static final int LINE_NUM = 1024;
    static final int COLUMN_NUM = 1024;

    public static void main(String[] args){
        long [][] array = new long[LINE_NUM][COLUMN_NUM];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++){
            for (int j = 0; j < COLUMN_NUM; j++){
                array[i][j] = i*2 + j;
            }
        }
        long endTime = System.currentTimeMillis();
        long cacheTime = endTime - startTime;
        System.out.println("cache time :" + cacheTime);

        startTime = System.currentTimeMillis();
        for (int j = 0; j < COLUMN_NUM; j++){
            for (int i = 0; i < LINE_NUM; i++){
                array[i][j] = i*2 + j;
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("no cache time :" + (endTime - startTime));
    }
    /*
    * 解决伪共享方法一：cache行为64B,对象头8B，加上变量8B，加上填充的6个无用变量48B，达到一个cache行对应一个变量
    *
    */
    public final static class FiledLong{
        public volatile long value = 0L;
        public long p1,p2,p3,p4,p5,p6;
    }
    /*
     * 解决伪共享方法二：sun.misc.Contended注解
     *
     */
    @Contended
    public final static class FiledLong2{
        public volatile long value = 0L;
    }
}
