package com.app.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端启动
 * @author:wq
 * @date:2020/3/13
 * @description:com.app.rmi
 * @version:1.0
 */
public class Server {

    public static void main(String[] args) {
        try {
            PersonService personService = new PersonServiceImpl();
            //提供服务的端口
            LocateRegistry.createRegistry(5555);
            //注册接口命名
            Context namingContext = new InitialContext();
            namingContext.rebind("rmi://localhost:5555/PersonService", personService);
            //Naming.rebind("rmi://localhost:6666/person-service",personService);
            System.out.println("Service started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
