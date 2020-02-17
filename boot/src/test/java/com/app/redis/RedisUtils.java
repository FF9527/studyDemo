package com.app.redis;

import com.app.redis.lock.RedisWithLock;
import io.rebloom.client.Client;
import javafx.scene.paint.Stop;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import javax.validation.constraints.Max;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    private static Jedis JEDIS = null;
    private static JedisPool jedisPool = null;
    private static final String HOST = "192.168.0.114";
//    private static final String HOST = "192.168.1.7";
    private static Client client = null;
    static{
//        JEDIS = new Jedis(HOST,6379,1000);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,HOST,6379);
        client = new Client(jedisPool);

    }

    public static Client getJReBloomClient(){
        return client;
    }

    public static Jedis getJedis(){
//        return jedis;
        Jedis jedis = jedisPool.getResource();
        if (jedis != null){
            return jedis;
        }else {
            jedis = jedisPool.getResource();
            if(jedis != null){
                return jedis;
            }
            return new Jedis(HOST,6379,1000);
        }

    }

    /**
     * 测试连接
     */
    public static void main(String[] args){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set("linkTest2","hello World2");
            String back = jedis.set("linkTest","hello World");
            System.out.println(("OK").equals(back));
            Object response = RedisUtils.eval(RedisWithLock.UNLOCK_EVAL, Arrays.asList("linkTest","linkTest2"), Arrays.asList("hello World","hello World2"));
            System.out.println(response);
        }finally {
            if(jedis != null){
                //释放jedispool的一个连接
                jedis.close();
            }
            //关闭jedispool
            close();
        }
    }

    /**
     * 封装方法
     */

    public interface CallWithRedis<T>{

        public T call(Jedis jedis);

    }

    private static <T> T execute(CallWithRedis<T> caller){
        try (Jedis jedis = getJedis()){
            return caller.call(jedis);
        }
    }

    public static String set(String key, String value, SetParams params){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.set(key,value,params);
            }
        });
    }

    public static String set(String key, String value){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.set(key,value);
            }
        });
    }

    public static String get(String key){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public static Long del(String key){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    public static Long expire(String key, int seconds){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.expire(key,seconds);
            }
        });
    }

    public static Object eval(String script, List<String> keys, List<String> value){
        return RedisUtils.execute(new CallWithRedis<Object>() {
            @Override
            public Object call(Jedis jedis){
                return jedis.eval(script,keys,value);
            }
        });
    }

    public static Long rpush(String key, String value){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis){
                return jedis.rpush(key,value);
            }
        });
    }

    public static String lpop(String key){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis){
                return jedis.lpop(key);
            }
        });
    }

    public static String lindex(String key,Long index){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis){
                return jedis.lindex(key,index);
            }
        });
    }

    public static Long llen(String key){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    public static Long zadd(String key, double score, String value){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.zadd(key,score,value);
            }
        });
    }

    public static Set<String> zrangeByscore(String key, double min, double max, int offset, int count){
        return RedisUtils.execute(new CallWithRedis<Set<String>>() {
            @Override
            public Set<String> call(Jedis jedis) {
                return jedis.zrangeByScore(key,min,max,offset,count);
            }
        });
    }

    public static Long zrem(String key, String value){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.zrem(key,value);
            }
        });
    }

    public static Set<String> zrange(String key, long start, long Stop){
        return RedisUtils.execute(new CallWithRedis<Set<String>>() {
            @Override
            public Set<String> call(Jedis jedis) {
                return jedis.zrange(key,start,Stop);
            }
        });
    }

    public static Long zcard(String key){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }

    public static Pipeline pipelined(){
        return RedisUtils.execute(new CallWithRedis<Pipeline>() {
            @Override
            public Pipeline call(Jedis jedis) {
                return jedis.pipelined();
            }
        });
    }

    public static Object watch(CallWithRedis caller){
        return RedisUtils.execute(caller);
    }

    public static String watch(String key){
        return RedisUtils.execute(new CallWithRedis<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.watch(key);
            }
        });
    }

    public static Transaction multi(){
        return RedisUtils.execute(new CallWithRedis<Transaction>() {
            @Override
            public Transaction call(Jedis jedis) {
                return jedis.multi();
            }
        });
    }

    public static Long incr(String key){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    public static Boolean setBit(String key, int offset, boolean value){
        return RedisUtils.execute(new CallWithRedis<Boolean>() {
            @Override
            public Boolean call(Jedis jedis) {
                return jedis.setbit(key,offset,value);
            }
        });
    }

    public static Long bitCount(String key){
        return RedisUtils.execute(new CallWithRedis<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.bitcount(key);
            }
        });
    }

    public static void close(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }
}
