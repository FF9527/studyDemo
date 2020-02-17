package com.app.redis.transaction;

import com.alibaba.fastjson.JSON;
import com.app.redis.RedisUtils;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

/**
 * @author:wuqi
 * @date:2020/2/12
 * @description:com.app.redis.transaction
 * @version:1.0
 */
public class TransactionTest {

    public static void main(String[] args){
        Pipeline pipeline = RedisUtils.pipelined();
        pipeline.multi();
        pipeline.incr("int");
        pipeline.incr("int");
        Response<List<Object>> response = pipeline.exec();
        pipeline.close();//get前需要关闭管道
        System.out.println(response.get());
        RedisUtils.close();
    }
}
