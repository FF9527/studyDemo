package com.app.classLoader;

/**
 * @author:wuqi
 * @date:2019/12/28
 * @description:com.app.study.classLoader
 * @version:1.0
 */
public class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLOWORLD = "hello world";
}
