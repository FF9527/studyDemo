package com.app.alg;

import java.util.LinkedHashMap;

/**
 * @author:wuqi
 * @date:2020/3/18
 * @description:com.app.alg
 * @version:1.0
 */
public class LRUMap<K,V> {
    //队列最大值
    private int size;
    //队列存储实际数据的容器
    private LinkedHashMap<K,V> map;

    public LRUMap(int size){
        this.size = size;
        this.map = new LinkedHashMap<>(size);
    }

    public void put(K key,V value){
        //命中
        if(map.get(key) != null){
            map.remove(key);
            map.put(key,value);
            return;
        }
        //未命中
        if (map.size() < size){
            //队列未达到最大值
            map.put(key,value);
        }else {
            //列表达到最大值，先删除最久未使用
            removeLru(map);
            map.put(key, value);
        }
    }

    private void removeLru(LinkedHashMap<K,V> map){
        K needRemove = null;
        int i = 0;
        for (K k : map.keySet()) {
            if(i == 0){
                needRemove = k;
                break;
            }
        }
        map.remove(needRemove);
    }

    public V get(K key){
        V value = map.get(key);
        map.put(key,value);
        return value;
    }

    public static void main(String[] args) {
        LRUMap<String,String> map = new LRUMap(4);
        map.put("1","a");
        map.put("2","b");
        map.put("3","d");
        map.put("2","c");
        map.put("4","e");
        map.put("5","f");
        System.out.println(map.toString());
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
