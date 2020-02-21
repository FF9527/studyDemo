package com.app.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author:wuqi
 * @date:2020/2/20
 * @description:com.app.kafka
 * @version:1.0
 */
public class ProducerTest {

    private static final String HOST = "192.168.0.101";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers",HOST + ":9091"+","+HOST + ":9092"+","+HOST + ":9093");//指定broker，配置部分就可以了，producer会替换成实际brokers列表
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");//key序列化类，无默认值，必须配置
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");//value序列化类，无默认值，必须配置，可与key不同
        props.put("acks","-1");//控制参数的持久性策略，须传入字符串参数
        props.put("retries","3");//重试次数，默认为0
        props.put("batch.size","16384");//一个批次大小，默认16KB，producer调优重要参数
        props.put("linger.ms","10");//发送消息延时，默认为0，消息立即发送，不需要batch满，producer调优重要参数
        props.put("buffer.memory","33554432");//消息缓冲区大小默认，32MB，producer调优重要参数
        props.put("max.block.ms","3000");//等待元数据超时时间，（例如发送topic，客户端未缓存）
        props.put("max.request.size","10485760");//请求消息的最大值，默认10MB，由于消息头开销需要比实际最大值大
        props.put("request.timeout.ms","3000");//请求超时时间
        try(Producer producer = new KafkaProducer(props);) {
            producer.send(new ProducerRecord("topic-test","hello world"));
            //异步发送+回调
            producer.send(new ProducerRecord("topic-test", "test1"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null){
                        System.out.println("message send success");
                    }else {
                        System.out.println("message send fail");
                    }
                }
            });
            producer.send(new ProducerRecord("topic-test", "test2"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null){
                        System.out.println("message send success");
                    }else {
                        System.out.println("message send fail");
                    }
                }
            });
            //利用future的get()方法实现同步发送
            producer.send(new ProducerRecord("topic-test", "test1")).get();
            producer.send(new ProducerRecord("topic-test", "test2")).get();
        }
    }
}
