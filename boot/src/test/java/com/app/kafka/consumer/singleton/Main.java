package com.app.kafka.consumer.singleton;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka.consumer.singleton
 * @version:1.0
 */
public class Main {

    public static void main(String[] args){
        ConsumerThreadHandler handler = new ConsumerThreadHandler("mcip:9091","test","topic-test");
        int cpuCount = Runtime.getRuntime().availableProcessors();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.consume(cpuCount);
            }
        }).start();
        try {
            Thread.sleep(30000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        handler.close();
    }
}
