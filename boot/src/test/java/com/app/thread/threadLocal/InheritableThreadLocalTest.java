package com.app.thread.threadLocal;

/**
 * @author:wuqi
 * @date:2020/1/5
 * @description:com.app.thread.threadLocal
 * @version:1.0
 * InheritableThreadLocal保存到当前线程的inhertableThreadLocals中, 是在init()方法中复制父线程的inhertableThreadLocals
 */
public class InheritableThreadLocalTest {

    static ThreadLocal<String> inheritableLocal = new InheritableThreadLocal<>();

    static void print(String str){
        System.out.println(str + ": parents variable :" + inheritableLocal.get());
    }

    public static void main(String[] args) throws InterruptedException {
        inheritableLocal.set("mainThread variable");//父线程先赋值
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                print("threadOne");
            }
        });
        inheritableLocal.remove();//变量拷贝所以threadOne中不会清除
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                print("threadTwo");
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}
