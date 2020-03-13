package com.app.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 客户端启动
 * @author:wq
 * @date:2020/3/13
 * @description:com.app.rmi
 * @version:1.0
 */
public class Client {

    public static void main(String[] args) {
        try {
            //远程对象调用的端口和注册类
            PersonService personService = (PersonService) Naming.lookup("rmi://localhost:5555/PersonService");
            Person person = personService.getPersonInfo(5);
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
