package com.app.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 服务端接口，必须继承Remote，方法必须throws RemoteException
 * @author:wq
 * @date:2020/3/13
 * @description:com.app.rmi
 * @version:1.0
 */
public interface PersonService extends Remote {

    Person getPersonInfo(int n) throws RemoteException;

}
