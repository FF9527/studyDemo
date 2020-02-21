package com.app.kafka.consumer.singleton;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka.consumer.singleton
 * @version:1.0
 */
public class ConsumerThreadHandler {

    private KafkaConsumer<String,String> consumer;
    private ExecutorService executors;
    private Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

    public ConsumerThreadHandler(String brokerList, String groupId, String topic){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener() {
            //rebalance前
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                consumer.commitSync();
            }
            //relalance后
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                offsets.clear();
            }
        });
    }

    public void consume(int threadNumber){
        executors = Executors.newFixedThreadPool(threadNumber);
        try {
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofSeconds(1));
                if (records.isEmpty()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        continue;
                    }
                }else {
                    executors.submit(new ConsumerWorker(records,offsets));
                }
            }
        }finally {
            commitOffsets();
            consumer.close();
        }
    }

    private void commitOffsets(){
        Map<TopicPartition,OffsetAndMetadata> unmodfiedMap;
        synchronized (offsets){
            if (offsets.isEmpty()){
                return;
            }
            unmodfiedMap = Collections.unmodifiableMap(new HashMap<>(offsets));
            offsets.clear();
        }
    }

    public void close() {
        consumer.close();
        executors.shutdown();
    }
}
