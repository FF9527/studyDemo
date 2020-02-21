package com.app.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka
 * @version:1.0
 */
public class AssignTest {

    public static void main(String[] args){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"mcip:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"15000");
        try(Consumer<String,String> consumer = new KafkaConsumer<>(props);){
            List<PartitionInfo> partitionInfos = consumer.partitionsFor("topic-test");
            List<TopicPartition> partitions = new ArrayList<>();
            if (partitions != null){
                for (PartitionInfo partitionInfo : partitionInfos) {
                    if (partitionInfo.partition() == 1){
                        partitions.add(new TopicPartition(partitionInfo.topic(),partitionInfo.partition()));
                    }
                }
            }
            consumer.assign(partitions);

            while (true){
                ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println("value = "+record.value()+",partition = "+record.partition()+",offset = "+record.offset());
                }
            }
        }

    }

}
