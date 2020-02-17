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

    static class Hello{
        public void hello(){
            System.out.println("hello world");
        }
        public void sayHello1() {
            System.out.println("hello world1");
        }
    }


    public static void main(String[] args){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\myGItHub\\springBootDemo\\com\\sun\\proxy");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before method run...");
                Object result = methodProxy.invokeSuper(o, objects);
                System.out.println("after method run...");
                return result;
            }
        });
        Hello hello = (Hello) enhancer.create();
        //test.test();
        System.out.println("proxy.toString:"+hello.toString());//proxy.toString:com.app.proxy.Hello@eed1f14
        System.out.println("proxy.hashCode:"+hello.hashCode());//proxy.hashCode:250421012
    }
}
