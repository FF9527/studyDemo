package com.app.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:wuqi
 * @date:2020/3/21
 * @description:com.app.net
 * @version:1.0
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("a client connect!");
            InputStream in = socket.getInputStream();
            ObjectInputStream objStream = new ObjectInputStream(in);
            System.out.println(objStream.readUTF());
            objStream.close();
            in.close();
            socket.close();
        }

    }
}
