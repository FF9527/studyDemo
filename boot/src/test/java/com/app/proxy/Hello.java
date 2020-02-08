package com.app.proxy;

/**
 * @author:wuqi
 * @date:2019/12/30
 * @description:com.app.proxy
 * @version:1.0
 */
public class Hello implements IHello {

    @Override
    public void sayHello() {
        System.out.println("hello world");
    }


    public void sayHello1() {
        System.out.println("hello world1");
    }
}
