package com.app.redis.practice;

import com.app.redis.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author:wuqi
 * @date:2020/2/15
 * @description:com.app.redis.practice
 * @version:1.0
 */
public class SimpleRateLimiter {

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount){
        Response<Long> count = null;
        try (Jedis jedis = RedisUtils.getJedis();){
            String key = String.format("hist:%s:%s", userId, actionKey);
            long nowTs = System.currentTimeMillis();
            Pipeline pipeline = jedis.pipelined();
            pipeline.multi();
            pipeline.zadd(key, nowTs, "" + nowTs);
            pipeline.zremrangeByScore(key, 0, nowTs - period * 1000);
            count = pipeline.zcard(key);
            pipeline.expire(key,period + 1);
            pipeline.exec();
            pipeline.close();
            if (count.get() <= maxCount){
                //元素加入滑动窗口，元素总数<滑动窗口大小
                return true;
            }else{
                //元素加入滑动窗口，元素总数>滑动窗口大小,拒绝加入
                jedis.zrem(key,nowTs +"");
                return false;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleRateLimiter limiter = new SimpleRateLimiter();
        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("user","login",60,5));
            Thread.sleep(5000);
        }
        RedisUtils.close();
    }
}
