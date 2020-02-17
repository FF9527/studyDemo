package com.app.redis.lock;

import com.app.redis.RedisUtils;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author:wuqi
 * @date:2020/2/9
 * @description:com.app.redis.lock
 * @version:1.0
 */
public class RedisWithLock implements Lock {

    private String key;
    private ThreadLocal<String> valueLocal = new ThreadLocal();

    /**
     * KEYS[1] == Arrays.asList(key).get(0)
     * ARGV[1] == Arrays.asList(value).get(0)
     */
    public static final String UNLOCK_EVAL =
            "if redis.call('get',KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "    end";

    public RedisWithLock(String key){
        this.key = key;
    }

    @Override
    public void lock() {
        String value = new Random().nextLong() + "";
        valueLocal.set(value);
        for (;;){
            if ("OK".equals(RedisUtils.set(key,valueLocal.get(),SetParams.setParams().nx().ex(5)))){
                String val = valueLocal.get();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String lock = null;
                            for(;;){
                                lock = RedisUtils.get(key);
                                if(lock != null && lock.equals(val)){
                                    RedisUtils.expire(key,5);
                                    Thread.sleep(4000);
                                }else{
                                    break;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.setDaemon(true);
                thread.start();
                break;
            }
        }
    }

    @Override
    public void unlock() {
        Object response = RedisUtils.eval(UNLOCK_EVAL, Arrays.asList(key), Arrays.asList(valueLocal.get()));
        if(Integer.valueOf(response.toString()) == 0){
            System.out.println("解锁失败");
        }
        valueLocal.remove();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();

    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
