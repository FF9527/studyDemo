package com.app.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:wuqi
 * @date:2020/3/21
 * @description:com.app.net
 * @version:1.0
 */
public class TalkRoomA {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket accept = serverSocket.accept();
        System.out.println("room connected!");
        InputStream in = accept.getInputStream();
        OutputStream out = accept.getOutputStream();
        ObjectInputStream objin = new ObjectInputStream(in);
        ObjectOutputStream objout = new ObjectOutputStream(out);

        
    }
}
