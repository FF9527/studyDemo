package com.app.juc.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:wuqi
 * @date:2020/2/6
 * @description:com.app.concurrent.practice
 * @version:1.0
 */
public class SimpleDateFormatTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(format.parse("2020-02-06 17:32:00"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
