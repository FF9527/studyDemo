package com.app.kafka;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author:wuqi
 * @date:2020/2/21
 * @description:com.app.kafka
 * @version:1.0
 */
public class ConsumerTest {

    public static void main(String[] args){
        Properties props = new Properties();
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"mcip:9091,mcip:9092,mcip:9093");
        props.put("bootstrap.servers","mcip:9091,mcip:9092,mcip:9093");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id","test");//groupId，必须配置
        props.put("enable.auto.commit","true");//自动提交位移
        props.put("auto.commit.interval.ms","1000");//自动提交位移时间间隔
        props.put("auto.offset.reset","earliest");//从未读取过的最早的的消息开始读取，
        props.put("session.timeout.ms","10000");//coordinator检测失败的最大时间，默认10s，即10s内consumer未响应coordinator，则认为不可用
        props.put("max.poll.interval.ms","10000");//consumer处理消息最大时间
        props.put("fetch.max.bytes","10485760");//单次获取消息的最大字节数
        props.put("max.poll.records","500");//单次获取的最大消息数，默认500条
        props.put("connections.max.idle.ms","540000");//Kafka定期关闭socket连接的时间，默认9分钟
        try(Consumer<String,String> consumer = new KafkaConsumer<>(props);){
            //订阅具体topic
            consumer.subscribe(Arrays.asList("topic-test"));
            //也可采用正则订阅
            consumer.subscribe(Pattern.compile("topic-*"));
            while (true){
                ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                if (consumerRecords.isEmpty()){
                    //手动提交,上面enable.auto.commit需=false
                    //consumer.commitAsync();
                    break;
                }
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println("value = "+record.value()+",partition"+record.partition()+",offset = "+record.offset());
                }
            }
        }

    }
}
