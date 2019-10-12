package com.dream.lmy.mydream.socket.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAndroid implements Runnable {
    @Override
    public void run() {
        Socket socket = null;
        try {
            ServerSocket server = new ServerSocket(18888);
            // 循环监听客户端链接请求
            while (true) {
                System.out.println("start...");
                // 接收请求
                socket = server.accept();
                System.out.println("accept...");
                // 接收客户端消息
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                System.out.println(message);
                // 发送消息，向客户端
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println("Server:" + message);
                // 关闭流
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 启动服务器
    public static void main(String[] args) {
        Thread server = new Thread(new ServerAndroid());
        server.start();
    }
}
