package com.app.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Properties;

/**
 * @author:wuqi
 * @date:2020/2/17
 * @description:com.app.kafka
 * @version:1.0
 */
public class SpringConsumer {

    private static final String HOST = "192.168.0.109";
    private static ConcurrentMessageListenerContainer<String,String> listenerContainer;

    static{
        Properties pro = new Properties();
        pro.put("bootstrap.servers",HOST + ":9091"+","+HOST + ":9092"+","+HOST + ":9093");
        pro.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("group.id","test");
        pro.put("enable.auto.commit","true");
        pro.put("auto.commit.interval.ms","1000");
        pro.put("auto.offset.reset","earliest");
        ConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory(pro);
        ContainerProperties containerProperties = new ContainerProperties("topic-test");
        containerProperties.setMessageListener(new Listener<String,String>());
        listenerContainer = new ConcurrentMessageListenerContainer<>(consumerFactory,containerProperties);
    }

    static class Listener<K,V> implements MessageListener<K,V> {
        @Override
        public void onMessage(ConsumerRecord<K, V> consumerRecord) {
            System.out.println(consumerRecord.key());
            System.out.println(consumerRecord.value());
            listenerContainer.stop();
        }
    }

    public static void main(String[] args){
        listenerContainer.start();
    }

}
