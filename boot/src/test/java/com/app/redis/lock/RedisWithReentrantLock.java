package com.app.redis.lock;

import com.app.DateUtils;
import com.app.redis.RedisUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * redis实现分布式可重入锁
 * @author:wuqi
 * @date:2020/2/8
 * @description:com.app.redis.lock
 * @version:1.0
 */
public class RedisWithReentrantLock {

    private String key;

    private ThreadLocal<Map<String,Integer>> local = new ThreadLocal<Map<String, Integer>>();

    public RedisWithReentrantLock(String key){
        this.key = key;
    }

    private Map<String,Integer> getLocalMap(){
        Map<String,Integer> map = local.get();
        if (map != null){
            return map;
        }
        local.set(new HashMap<>());
        return local.get();
    }

    public void lock() {
        Map<String,Integer> map = getLocalMap();
        Integer refCount = map.get(key);
        if(refCount != null){
            map.put(key,++refCount);
            _refreshExpire(key);
            return ;
        }
        boolean ok = _lock(key);
        while(!ok){
            ok = _lock(key);
        }
        map.put(key,1);
    }

    public void unlock(){
        Map<String,Integer> map = getLocalMap();
        Integer refCount = map.get(key);
        if(refCount == null){
            System.out.println("no lock no unlock");
            return ;
        }
        if(--refCount > 0){
            map.put(key,refCount);
        }else {
            local.remove();
            _unlock(key);
        }
    }

    private boolean _lock(String key){
        return RedisUtils.set(key,"reentrantLock",SetParams.setParams().nx().ex(60)) != null;
    }

    private void _unlock(String key){
        RedisUtils.del(key);
    }

    private void _refreshExpire(String key){
        RedisUtils.expire(key,60);
    }

}
