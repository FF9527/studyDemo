package com.app.kafka.consumer.singleton;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka.consumer.singleton
 * @version:1.0
 */
public class ConsumerWorker implements Runnable {

    private ConsumerRecords<String,String> records;
    private Map<TopicPartition,OffsetAndMetadata> offsets;

    public ConsumerWorker(ConsumerRecords<String,String> records,Map<TopicPartition,OffsetAndMetadata> offsets){
        this.records = records;
        this.offsets = offsets;
    }

    @Override
    public void run() {
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String,String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                System.out.println("thread = "+Thread.currentThread().getName()+",value = "+record.value()+",partition"+record.partition()+",offset = "+record.offset());
            }
            long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            synchronized (offsets){
                if (!offsets.containsKey(partition)){
                    offsets.put(partition, new OffsetAndMetadata(lastOffset +1));
                }else{
                    long curr = offsets.get(partition).offset();
                    if (curr <= lastOffset + 1){
                        offsets.put(partition, new OffsetAndMetadata(lastOffset + 1));
                    }
                }
            }
        }
    }
}
