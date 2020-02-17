package com.app.juc.threadLocalRandom;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author:wuqi
 * @date:2020/1/6
 * @description:com.app.concurrent.threadLocalRandom
 * @version:1.0
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0;i < 10; i++){
            System.out.println(random.nextInt(5));
        }
    }
}
