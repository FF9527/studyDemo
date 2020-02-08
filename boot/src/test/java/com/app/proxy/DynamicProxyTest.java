package com.app.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author:wuqi
 * @date:2019/12/30
 * @description:com.app.proxy
 * @version:1.0
 */
public class DynamicProxyTest {

    public static void main(String[] args) {
        IHello target = new Hello();
        System.out.println(target.getClass().getClassLoader());
        System.out.println(Arrays.toString(target.getClass().getInterfaces()));
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello) new DynamicProxy().bind(target);
        System.out.println("proxy.hashCode:"+hello.hashCode());
        hello.sayHello();
        hello = null;
        System.gc();
        hello = (IHello) new DynamicProxy().bind(target);
        System.out.println("proxy.hashCode:"+hello.hashCode());

        //System.out.println("target.toString:"+target.toString());//target.toString:com.app.proxy.Hello@eed1f14
        //System.out.println("target.hashCode:"+target.hashCode());//target.hashCode:250421012
        // System.out.println("proxy.toString:"+hello.toString());//proxy.toString:com.app.proxy.Hello@eed1f14
        // System.out.println("proxy.hashCode:"+hello.hashCode());//proxy.hashCode:250421012
        //  System.out.println("target == proxy:"+(target == hello));//target == proxy:false

    }
}
