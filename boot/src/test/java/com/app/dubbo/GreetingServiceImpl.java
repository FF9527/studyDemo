package com.app.dubbo;

/**
 * @author:wuqi
 * @date:2020/3/12
 * @description:com.app.dubbo
 * @version:1.0
 */
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
