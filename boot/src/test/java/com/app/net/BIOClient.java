package com.app.net;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author:wuqi
 * @date:2020/3/21
 * @description:com.app.net
 * @version:1.0
 */
public class BIOClient {
    public static void main(String[] args) throws IOException {
        while(true){
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(8080));
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objout = new ObjectOutputStream(out);
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
            System.out.println("write : " + str);
            objout.writeUTF(str);
            objout.close();
            out.close();
        }
    }
}
