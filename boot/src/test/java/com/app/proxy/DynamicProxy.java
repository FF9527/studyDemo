package com.app.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author:wuqi
 * @date:2019/12/30
 * @description:com.app.proxy
 * @version:1.0
 */
public class DynamicProxy implements InvocationHandler {

    Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("sayHello")){
            System.out.println("welcome");
        }
        return method.invoke(target, args);
    }
}
