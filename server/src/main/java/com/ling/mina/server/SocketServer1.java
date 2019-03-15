package com.ling.mina.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer1 {
    public static void main(String[] args){
        SocketServer1 socketServer = new SocketServer1();
        socketServer.startServer(2333);
    }

    private void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动,等待客户连接...");
            //调用accept()方法开始监听，等待客户端的连接 这个方法会阻塞当前线程
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

            String receivedMsg = null;
            while ((receivedMsg = bufferedReader.readLine()) != null){
                System.out.println("收到客户端的消息：" + receivedMsg);
                bufferedWriter.write("我是服务器，我收到消息了：" + socket.getLocalAddress().getHostAddress() + "\n");
                bufferedWriter.flush();
            }
            socket.close();
            serverSocket.close();
            bufferedWriter.close();
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
