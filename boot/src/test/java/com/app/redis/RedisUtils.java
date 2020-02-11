package com.app.redis;

import com.app.redis.lock.RedisWithLock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.List;

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
    static{
//        JEDIS = new Jedis(HOST,6379,1000);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,HOST,6379);
    }

    private static Jedis getJedis(){
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

    interface CallWithRedis<T>{

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

    public static void close(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }
}
