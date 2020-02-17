package com.app.classLoader;

/**
 * @author:wuqi
 * @date:2019/12/28
 * @description:com.app.study.classLoader
 * @version:1.0
 */
public class NotInitialization {

    public static void main(String[] args){
        System.out.println(SubClass.value);
        System.out.println("-------------------");
        SuperClass[] sca = new SuperClass[10];
        System.out.println("-------------------");
        System.out.println(ConstClass.HELLOWORLD);
    }
    /*SuperClass init!
            123
            -------------------
            -------------------
    hello world*/
}
