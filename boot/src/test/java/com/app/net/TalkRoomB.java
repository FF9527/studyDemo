package com.app.net;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author:wuqi
 * @date:2020/3/21
 * @description:com.app.net
 * @version:1.0
 */
public class TalkRoomB {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8080));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream in = socket.getInputStream();
                     ObjectInputStream objin = new ObjectInputStream(in);) {
                    while (true) {
                        if (objin.read() > 0) {
                            String str = objin.readUTF();
                            System.out.println("receive : " + str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (OutputStream out = socket.getOutputStream();
                     ObjectOutputStream objout = new ObjectOutputStream(out);) {
                    Scanner scanner = new Scanner(System.in);
                    String str = scanner.next();
                    objout.writeUTF(str);
                    System.out.println("send : " + str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
