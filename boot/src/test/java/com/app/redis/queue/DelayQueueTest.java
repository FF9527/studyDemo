package com.app.redis.queue;

import com.app.redis.RedisUtils;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Delayed;

/**
 * @author:wuqi
 * @date:2020/2/12
 * @description:com.app.redis.queue
 * @version:1.0
 */
public class DelayQueueTest<E extends Delayed> extends AbstractQueue {

    private String qName;
    public DelayQueueTest(String qName){
        this.qName = qName;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return RedisUtils.zcard(qName).intValue();
    }

    @Override
    public boolean offer(Object o) {
        //延时5s
        return RedisUtils.zadd(qName,System.currentTimeMillis() + 5000,o.toString()) == 0;
    }

    @Override
    public String poll() {
        Set<String> values = RedisUtils.zrangeByscore(qName,0, System.currentTimeMillis(),0,1);
        if(values != null && !values.isEmpty()){
            String value = values.iterator().next();
            if(RedisUtils.zrem(qName,value) > 0){
                return value;
            }
        }
        return null;
    }

    @Override
    public String peek() {
        Set<String> values = RedisUtils.zrange(qName,0L,0L);
        if (values != null && !values.isEmpty()){
            String value = values.iterator().next();
            return value;
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException{
        DelayQueueTest delayQueue = new DelayQueueTest("delayQueue");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    delayQueue.offer(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        Thread.sleep(10000);
        thread.join();
        for (;;){
            if(delayQueue.peek() == null){
                break;
            }
            String value = delayQueue.poll();
            if(value != null){
                System.out.println(value);
            }
            Thread.sleep(2000);
        }
        RedisUtils.close();
    }
}
