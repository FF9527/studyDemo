package com.app.redis.practice;

import com.app.redis.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author:wuqi
 * @date:2020/2/14
 * @description:com.app.redis.practice
 * @version:1.0
 */
public class BitTest {

    public static void main(String[] args){
        Random random = new Random();
        int rm;
        int count = 0;
        try (Jedis jedis = RedisUtils.getJedis();){
            for (int i = 0; i < 365; i++) {
                rm = random.nextInt(500);
                if (rm%2 == 0){
                    jedis.setbit("bitTest",i,true);
                    count++;
                }
            }
            System.out.println("count : " + count);
            System.out.println("bitCount : " + jedis.bitcount("bitTest"));
        }
        RedisUtils.close();
    }
}
