package com.app.juc.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author:wuqi
 * @date:2020/2/2
 * @description:com.app.concurrent.queue
 * @version:1.0
 */
public class DelayQueueTest {

    public static void main(String[] args){
        DelayQueue<DelayedEle> delayQueue = new DelayQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            DelayedEle ele = new DelayedEle(random.nextInt(500), "task" + i);
            delayQueue.offer(ele);
        }
        DelayedEle eleTake = null;
        try {
            for (;;){
                while ((eleTake = delayQueue.take()) != null){
                    System.out.println(eleTake.toString());
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    static class DelayedEle implements Delayed {

        private final long delayTime;
        private final long expire;
        private String taskName;

        public DelayedEle(long delayTime, String taskName){
            this.delayTime = delayTime;
            this.taskName = taskName;
            this.expire = System.currentTimeMillis() + delayTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DelayedEle{");
            sb.append("delay=").append(delayTime);
            sb.append(", expire=").append(expire);
            sb.append(", taskName=").append(taskName);
            sb.append("}");
            return sb.toString();
        }
    }

}
