package com.app.kafka;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author:wuqi
 * @date:2020/2/17
 * @description:com.app.kafka
 * @version:1.0
 */
public class SpringProducer {

    private static KafkaTemplate<String,String> kafkaTemplate = null;
    private static final String HOST = "192.168.0.109";

    static{
        Properties pro = new Properties();
        pro.put("bootstrap.servers",HOST + ":9091"+","+HOST + ":9092"+","+HOST + ":9093");
        pro.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pro.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pro.put("acks","-1");
        pro.put("retries","3");
        pro.put("batch.size","323840");
        pro.put("linger.ms","10");
        pro.put("buffer.memory","33554432");
        pro.put("max.block.ms","3000");
        ProducerFactory producerFactory = new DefaultKafkaProducerFactory(pro);
        kafkaTemplate = new KafkaTemplate<String,String>(producerFactory,true);
    }

    public static ListenableFuture<SendResult<String, String>> send(String topic, String message){
        return kafkaTemplate.send(topic,message);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> future = send("tttttopic","replica test4");
        System.out.println(future.get().getProducerRecord().value());
    }

}
