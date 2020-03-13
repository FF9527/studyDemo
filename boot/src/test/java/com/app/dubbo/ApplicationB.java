package com.app.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author:wuqi
 * @date:2020/3/12
 * @description:com.app.dubbo
 * @version:1.0
 */
public class ApplicationB {
    private static String zookeeperHost = System.getProperty("zookeeper.address","mcip");

    public static void main(String[] args) throws InterruptedException {
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://mcip:2291?backup=mcip:2292,mcip:2293"));
        reference.setInterface(GreetingService.class);
        GreetingService service =reference.get();
        System.out.println(service.sayHi("dubbo"));
    }
}