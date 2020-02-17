package com.app.redis.practice;

import com.app.redis.RedisUtils;
import io.rebloom.client.Client;
import redis.clients.jedis.Jedis;

/**
 * @author:wuqi
 * @date:2020/2/15
 * @description:com.app.redis.practice
 * @version:1.0
 */
public class RedisBloomTest {

    public static void main(String[] args){
        try (Client client = RedisUtils.getJReBloomClient()){
            client.delete("codehole");
            for (int i = 0; i < 10000; i++) {
                client.add("codehole","user" + i);
            }
            for (int i = 0; i < 10000; i++) {
                boolean ret = client.exists("codehole","user" + i);
                if (!ret){//判断为不存在的，实际存在的数据
                    System.out.println("user" + i);//为空
                }
            }
            System.out.println("-----------------------------");
            for (int i = 5000;i < 15000;i++){
                boolean ret = client.exists("codehole","user" + i);
                if (ret && i > 10000){//判断为存在，实际不存在的
                    System.out.println("user" + i);//很多
                }
            }
        }
        RedisUtils.close();
    }
}
