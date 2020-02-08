package com.app.concurrent.COW;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author:wuqi
 * @date:2020/1/7
 * @description:com.app.concurrent.COW
 * @version:1.0
 */
public class COWTest {

    private static volatile CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        cowList.add("hello");
        cowList.add("world");
        cowList.add("skip1");
        cowList.add("skip2");
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                cowList.set(0,"hello ");
                cowList.remove(3);
                cowList.remove(2);
            }
        });

        Iterator<String> iterator = cowList.iterator();

        threadOne.start();
        threadOne.join();

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println(cowList);
    }
}
