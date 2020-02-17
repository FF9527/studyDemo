package com.app.redis.cluster;

import org.apache.catalina.Host;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author:wuqi
 * @date:2020/2/13
 * @description:com.app.redis.cluster
 * @version:1.0
 */
public class ClusterTest {

    /**
     * 创建单例
     */

    private ClusterTest() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static JedisCluster jedisCluster = null;
    private static final String HOST = "192.168.0.114";
    static{
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort(HOST,6379));
        nodes.add(new HostAndPort(HOST,6389));
        nodes.add(new HostAndPort(HOST,6399));
        nodes.add(new HostAndPort(HOST,6279));
        nodes.add(new HostAndPort(HOST,6289));
        nodes.add(new HostAndPort(HOST,6179));
        nodes.add(new HostAndPort(HOST,6189));
        jedisCluster = new JedisCluster(nodes,config);

    }

    /**
     * 测试连接  keys与scan
     */
    public static void main(String[] args){
        try {
            jedisCluster.set("linkTest","hello World");
            jedisCluster.set("scanTest","hello World");
            jedisCluster.set("test","hello World");
            jedisCluster.set("test2","hello World");
            jedisCluster.set("test3","hello World");
            System.out.println(jedisCluster.get("linkTest"));
            Set<String> keys = new HashSet<>();
            Map<String,JedisPool> jedisPoolMap = jedisCluster.getClusterNodes();
            for (JedisPool jedisPool : jedisPoolMap.values()) {
                try (Jedis jedis = jedisPool.getResource()){
                    Set<String> nodeKeys = jedis.keys("*");
                    keys.addAll(nodeKeys);
                }
            }
            System.out.println(keys);
            keys.clear();
            for (JedisPool jedisPool : jedisPoolMap.values()){
                try (Jedis jedis = jedisPool.getResource()){
                    String cursor = "0";
                    for (;;){
                        ScanResult<String> result = jedis.scan(cursor,new ScanParams().match("*").count(1));
                        cursor = result.getCursor();
                        keys.addAll(result.getResult());
                        if ("0".equals(cursor)){
                            break;
                        }
                    }
                }
            }
            System.out.println(keys);
        }finally {
            close();
        }
    }

    public static void close(){
        if(jedisCluster != null){
            jedisCluster.close();
        }
    }
}
