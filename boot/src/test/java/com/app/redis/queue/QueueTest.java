package com.app.redis.queue;

import com.app.redis.RedisUtils;

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * @author:wuqi
 * @date:2020/2/12
 * @description:com.app.redis.queue
 * @version:1.0
 */
public class QueueTest<T> extends AbstractQueue {

    private String qName;

    public QueueTest(String qName){
        this.qName = qName;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return RedisUtils.llen(qName).intValue();
    }

    @Override
    public boolean offer(Object t) {
        //String value = JSON.toJSONString(t);
        return RedisUtils.rpush(qName,t.toString()) == 1;
    }

    @Override
    public String poll() {
        return RedisUtils.lpop(qName);
    }

    @Override
    public String peek() {
        return RedisUtils.lindex(qName,0L);
    }

    public static void main(String[] args){
        QueueTest queue = new QueueTest("queue");
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        System.out.println("size : " + queue.size());
        System.out.println("peek : " + queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        RedisUtils.close();
    }
}
