package com.app.redis.practice;

import com.app.redis.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * @author:wuqi
 * @date:2020/2/15
 * @description:com.app.redis.practice
 * @version:1.0
 */
public class HyperLogLogTest {

    public static void main(String[] args){
        try (Jedis jedis = RedisUtils.getJedis();){
            jedis.del("codehole");
            jedis.del("codeholee1");
            jedis.del("codeholee2");
            jedis.del("codeholeee1");
            jedis.del("codeholeee2");
            for (int i = 0; i < 10000; i++) {
                jedis.pfadd("codehole","user" + i);
                jedis.pfadd("codeholee1","user" + i);//与codehole中value相同，merge时去重
                jedis.pfadd("codeholee2","userr" + i);
            }
            System.out.println("10000 : " + jedis.pfcount("codehole"));
            System.out.println("10000 : " + jedis.pfcount("codeholee1"));
            System.out.println("10000 : " + jedis.pfcount("codeholee2"));
            jedis.pfmerge("codeholeee1","codeholee1","codehole");
            jedis.pfmerge("codeholeee2","codeholee2","codehole");
            System.out.println("20000 : " + jedis.pfcount("codeholeee1"));
            System.out.println("20000 : " + jedis.pfcount("codeholeee2"));

        }
        RedisUtils.close();
    }
}
