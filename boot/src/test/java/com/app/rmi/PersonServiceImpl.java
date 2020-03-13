package com.app.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 服务端：具体实现类，UnicastRemoteObject提供远程调用基础功能
 * @author:wq
 * @date:2020/3/13
 * @description:com.app.rmi
 * @version:1.0
 */
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {

    protected PersonServiceImpl() throws RemoteException {
    }

    @Override
    public Person getPersonInfo(int id) throws RemoteException {
        System.out.println("get Person :" + id);
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
