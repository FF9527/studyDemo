package com.app.kafka.consumer.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka.consumer.prototype
 * @version:1.0
 */
public class ConsumerGroup {

    private List<ConsumerRunnable> consumers;

    public ConsumerGroup(int consumerNum, String groupId, String topic, String brokerList){
        consumers = new ArrayList<>(consumerNum);
        for (int i = 0; i < consumerNum; i++) {
             consumers.add(new ConsumerRunnable(brokerList,groupId,topic));
        }
    }

    public void execute(){
        for (ConsumerRunnable consumer : consumers) {
            new Thread(consumer).start();
        }
    }

    public static void main(String[] args){
        ConsumerGroup group = new ConsumerGroup(3,"test","topic-test","mcip:9091");
        group.execute();
    }
}
