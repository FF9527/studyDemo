package com.app.redis.cluster;

import com.app.redis.RedisUtils;
import com.app.redis.lock.RedisWithLock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:wuqi
 * @date:2020/2/13
 * @description:com.app.redis.cluster
 * @version:1.0
 */
public class SentinelPoolTest {

    /**
     * 创建单例
     */

    private SentinelPoolTest() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static JedisSentinelPool jedisSentinelPool = null;
    private static final String HOST = "192.168.0.114";
    static{
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        Set<String> hosts = new HashSet<String>();
        hosts.add(HOST + ":26279");
        hosts.add(HOST + ":26289");
        hosts.add(HOST + ":26399");
        jedisSentinelPool = new JedisSentinelPool("master",hosts,config);

    }

    public static Jedis getJedis(){
        Jedis jedis = jedisSentinelPool.getResource();
        if (jedis != null){
            return jedis;
        }else {
            jedis = jedisSentinelPool.getResource();
            if(jedis != null){
                return jedis;
            }
        }
        return new Jedis(HOST,6379,1000);

    }

    /**
     * 测试连接
     */
    public static void main(String[] args){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String back = jedis.set("linkTest","hello World");
            System.out.println(("OK").equals(back));
        }finally {
            if(jedis != null){
                jedis.close();
            }
            close();
        }
    }

    public static void close(){
        if(jedisSentinelPool != null){
            jedisSentinelPool.close();
        }
    }
}
