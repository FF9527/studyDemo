package com.app.redis.transaction;

import com.app.redis.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author:wuqi
 * @date:2020/2/12
 * @description:com.app.redis.transaction
 * @version:1.0
 */
public class WatchTest {

    public static void main(String[] args){
        RedisUtils.CallWithRedis caller = new RedisUtils.CallWithRedis() {
            @Override
            public Object call(Jedis jedis) {
                for (;;){
//                    RedisUtils.watch("int");//watch multi exec必须是一个jedis实例
                    jedis.watch("int");
                    int value = Integer.parseInt(jedis.get("int"));
                    System.out.println("int : " + value);
//                    RedisUtils.incr("int");//这里修改操作可以是不同的连接
                    jedis.incr("int");
//                    Transaction transaction = RedisUtils.multi();//自动关闭连接会导致exec报错
                    Transaction transaction = jedis.multi();
                    transaction.set("int","0");
                    List<Object> res = transaction.exec();
                    if(res != null){
                        System.out.println("transaction success");
                        break;
                    }
                    System.out.println("watchError!");
                }
                return 0;
            }
        };
        RedisUtils.watch(caller);
        RedisUtils.close();
    }
}
