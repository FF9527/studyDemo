package com.app.redis.practice;

import com.app.redis.RedisUtils;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author:wuqi
 * @date:2020/2/15
 * @description:com.app.redis.practice
 * @version:1.0
 */
public class GeoHashTest {

    public static void main(String[] args){
        try(Jedis jedis = RedisUtils.getJedis();){
            //添加地点经纬度
            jedis.geoadd("addr",114.404105,30.511786,"GuangGu Square");
            jedis.geoadd("addr",114.367382,30.536487,"Wuhan University");
            jedis.geoadd("addr",114.420634,30.513279,"Huazhong University");
            jedis.geoadd("addr",114.411723,30.482473,"GuangGu Software Park");
            //两地点距离 默认M设置单位
            System.out.println(jedis.geodist("addr","Wuhan University","Huazhong University",GeoUnit.KM));
            System.out.println(jedis.geodist("addr","GuangGu Square","GuangGu Software Park"));
            //获取地点经纬度
            System.out.println(jedis.geopos("addr","GuangGu Square"));
            //获取地点hash值
            System.out.println(jedis.geohash("addr","GuangGu Square"));
            //搜索地点附近3KM以内的地点
            List<GeoRadiusResponse> res = jedis.georadiusByMember("addr","GuangGu Square",3,GeoUnit.KM);
            for (GeoRadiusResponse re : res) {
                System.out.println(re.getMemberByString());
            }
        }
        RedisUtils.close();
    }
}
