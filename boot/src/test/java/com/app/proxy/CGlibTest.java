package com.app.proxy;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author:wuqi
 * @date:2019/12/31
 * @description:com.app.cglib
 * @version:1.0
 */
public class CGlibTest {

    public static void main(String[] args){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\myGItHub\\springBootDemo\\com\\sun\\proxy");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
        Hello target = new Hello();
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before method run...");
                //这样实现代理对象都没有
                //Object result = methodProxy.invokeSuper(o, objects);
                Object result = methodProxy.invoke(target,objects);
                System.out.println("after method run...");
                return result;
            }
        });
        Hello proxy = (Hello) enhancer.create();
        System.out.println("proxy.toString:"+proxy.toString());//proxy.toString:com.app.proxy.Hello@2437c6dc
        System.out.println("proxy.hashCode:"+proxy.hashCode());//proxy.hashCode:607635164
        System.out.println("target.hashCode:"+target.hashCode());//target.hashCode:607635164
        System.out.println("target.toString:"+target.toString());//target.toString:com.app.proxy.Hello@2437c6dc
    }
}
