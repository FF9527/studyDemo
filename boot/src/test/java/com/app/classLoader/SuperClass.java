package com.app.classLoader;

/**
 * @author:wuqi
 * @date:2019/12/28
 * @description:com.app.study.classLoader
 * @version:1.0
 */
public class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}
