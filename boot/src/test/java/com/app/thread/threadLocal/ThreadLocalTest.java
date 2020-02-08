package com.app.thread.threadLocal;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.threadLocal
 * @version:1.0
 * ThreadLocal保存到当前线程threadLocals中 不具有继承性，父线程的threadLocalMap取不到
 */
public class ThreadLocalTest {

    static ThreadLocal<String> localVariable = new ThreadLocal<>();

    static void print(String str){
        System.out.println(str + ":" + localVariable.get());
        localVariable.remove();
    }

    public static void main(String[] args){
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadOne local variable");
                localVariable.set("threadOne local variable2");
                print("threadOne");
                System.out.println("threadOne remove after" + ":" + localVariable.get());
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadTwo local variable");
                print("threadTwo");
                System.out.println("threadTwo remove after" + ":" + localVariable.get());
            }
        });
        threadOne.start();
        threadTwo.start();
    }
}
