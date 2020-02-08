package com.app.concurrent.practice;

import netscape.javascript.JSObject;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:wuqi
 * @date:2020/2/6
 * @description:com.app.concurrent
 * @version:1.0
 */
public class ConcurrentHashMapTest {

    private static final ConcurrentHashMap<String,List<String>> map = new ConcurrentHashMap<>();

    @Test
    public void putTest(){
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device1");
                list1.add("device2");
                map.put("topic1", list1);
                System.out.println(map);
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device11");
                list1.add("device22");
                map.put("topic1", list1);
                System.out.println(map);
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device111");
                list1.add("device222");
                map.put("topic2", list1);
                System.out.println(map);
            }
        });
        pool.shutdown();

    }

    @Test
    public void putIfAbsentTest(){
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device1");
                list1.add("device2");
                List<String> oldList = map.putIfAbsent("topic1", list1);
                if(null != oldList){
                    oldList.addAll(list1);
                }
                System.out.println(map);
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device11");
                list1.add("device22");
                List<String> oldList = map.putIfAbsent("topic1", list1);
                if(null != oldList){
                    oldList.addAll(list1);
                }
                System.out.println(map);
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new CopyOnWriteArrayList<>();
                list1.add("device111");
                list1.add("device222");
                List<String> oldList = map.putIfAbsent("topic2", list1);
                if(null != oldList){
                    oldList.addAll(list1);
                }
                System.out.println(map);
            }
        });
        pool.shutdown();
    }
}
