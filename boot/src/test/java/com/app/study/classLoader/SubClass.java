package com.app.study.classLoader;

/**
 * @author:wuqi
 * @date:2019/12/28
 * @description:com.app.study.classLoader
 * @version:1.0
 */
public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}
