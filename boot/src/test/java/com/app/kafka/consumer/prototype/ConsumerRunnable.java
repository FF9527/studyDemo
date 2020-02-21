package com.app.kafka.consumer.prototype;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka.consumer.prototype
 * @version:1.0
 */
public class ConsumerRunnable implements Runnable {

    private KafkaConsumer<String,String> consumer = null;

    public ConsumerRunnable(String brokerList, String groupId, String topic){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
        while (true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(200));
            if (records.isEmpty()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    continue;
                }
            }else {
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("thread = "+Thread.currentThread().getName()+",value = "+record.value()+",partition"+record.partition()+",offset = "+record.offset());
                }
            }
        }
    }
}
