package com.app.idgenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:wuqi
 * @date:2020/3/27
 * @description:com.app.idgenerator
 * @version:1.0
 */
public class SnowFlakeIdGenerator {
    private final long version;//版本号 1bit
    private final long STARTTIMESTAMP = 1585286065061L;//创建ID生成器的时间
    private long timestamp;//时间戳毫秒级  41bit
    private final long mcid;//机器id 10bit
    private int seq = -1;//自增seq

    private long lastTimstamp = -1;//记录毫秒级
    private long lastSeql = 0;//记录同毫秒级的sql

    public SnowFlakeIdGenerator(long version,long mcid){
        //版本号校验
        if (version>1 || version <0){
            throw new IllegalArgumentException("version must in {0,1}");
        }
        //机器号校验
        if (mcid>1023 || mcid < 0){
            throw new IllegalArgumentException("mcid must in {0-1023}");
        }
        this.version = version;
        this.mcid = mcid;
    }

    /**
     * 同毫秒级seq自增，
     * 不同毫秒级seq归零
     * @return
     */
    private long getSeq(long current){
        if (lastTimstamp == current){
            //同毫秒级校验seq是否超出范围
            if (seq > 1022){
                throw new IllegalArgumentException("seq arrive max,generator failed!");
            }
        }else{
            //不同毫秒级seq归零
            lastTimstamp = current;
            seq = -1;
            System.out.println("============== 分割线 =================");
        }
        return ++seq;
    }

    private long generator(){
        long timestamp = System.currentTimeMillis()-STARTTIMESTAMP;
        long id = 0L;
        //1 + 41 + 10 + 12  由于时间处于高位，所以毫秒间是有序的
        id |= version << 63;
        id |= timestamp << 22;
        id |= mcid << 12;
        id |= getSeq(timestamp);
        return id;
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        //开两个线程模拟分布式两台机器
        //毫秒间是有序的
        //毫秒内是单机有序的,整体是无序的
        pool.submit(new Runnable() {
            @Override
            public void run() {
                SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(0L,0L);
                for (int i = 0; i < 1000; i++) {
                    System.out.println(idGenerator.generator());

                }
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                SnowFlakeIdGenerator idGenerator1 = new SnowFlakeIdGenerator(0,1L);
                for (int i = 0; i < 1000; i++) {
                    System.out.println(idGenerator1.generator());
                }
            }
        });
        pool.shutdown();
    }
}
