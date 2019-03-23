package com.ling.mina.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.startServer(2333);
    }

    private void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动,等待客户连接...");
            //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
            ExecutorService threadPool = Executors.newFixedThreadPool(100);
            while (true) {
                Runnable runnable = () -> {
                    try {
                        //调用accept()方法开始监听，等待客户端的连接 这个方法会阻塞当前线程
                        Socket socket = serverSocket.accept();
                        System.out.println("客户端连接成功");
                        //接收客户端消息
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //给客户端发送消息
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                        String receivedMsg;
                        while ((receivedMsg = bufferedReader.readLine()) != null && !("end").equals(receivedMsg)) {
                            System.out.println("客户端：" + receivedMsg);
                            String response = "hello client";
                            System.out.println("我(服务端)：" + response);
                            bufferedWriter.write(response+ "\n");
                            bufferedWriter.flush();
                        }
                        socket.close();
                        serverSocket.close();
                        bufferedWriter.close();
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                threadPool.submit(runnable);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void startServe1r(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动,等待客户连接...");
            //调用accept()方法开始监听，等待客户端的连接 这个方法会阻塞当前线程
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String receivedMsg;
            while ((receivedMsg = bufferedReader.readLine()) != null && !("end").equals(receivedMsg)) {
                System.out.println("客户端：" + receivedMsg);
                String response = "hello client";
                System.out.println("我(服务端)：" + response);
                bufferedWriter.write(response+ "\n");
                bufferedWriter.flush();
            }
            socket.close();
            serverSocket.close();
            bufferedWriter.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
