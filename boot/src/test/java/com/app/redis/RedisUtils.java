package com.app.redis;

import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

/**
 * @author:wuqi
 * @date:2020/2/8
 * @description:com.app.redis
 * @version:1.0
 */
public class RedisUtils {

    /**
     * 创建单例
     */

    private RedisUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

//    private static Jedis jedis = null;
    private static JedisPool jedisPool = null;
    static{
//        jedis = new Jedis("192.168.0.106",6379,1000);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,"192.168.0.106",6379);
    }

    private static Jedis getJedis(){
//        return jedis;
        return jedisPool.getResource();
    }

    public static void main(String[] args){
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            String back = jedis.set("linkTest","hello World");
//            System.out.println(("OK").equals(back));
//        }finally {
//            if(jedis != null){
//                jedis.close();
//            }
//        }
        System.out.println(1);
    }

    /**
     * 封装方法
     */

    public static String set(String key, String value, SetParams params){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key,value,params);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static String get(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static Long del(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static Long expire(String key, int seconds){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key,seconds);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static void close(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }

}
