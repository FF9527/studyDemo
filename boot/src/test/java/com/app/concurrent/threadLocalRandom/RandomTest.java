package com.app.concurrent.threadLocalRandom;

import java.util.Random;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.concurrent.threadLocalRandom
 * @version:1.0
 */
public class RandomTest {

    public static void main(String[] args){
        Random random = new Random();
        for (int i = 0; i < 10; i++){
            System.out.println(random.nextInt());//采用CAS保证原子性，多线程会使大量线程进行自旋重试，会降低并发性能。
        }
    }
}
