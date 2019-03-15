package com.ling.mina.minaself;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class SocketClient1 {
    public static void main(String[] args){
        SocketClient1 socketClient = new SocketClient1();
        try {
            socketClient.startClient(2334);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void startClient(int port) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1",port);
        System.out.println("客户端已启动");
        //给服务器发消息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //接收服务器传过来的消息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //键盘输入消息  发送给服务端
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String readLine = null;
        while (!(readLine = inputReader.readLine()).equals("end")){
            System.out.println("键盘输入消息：" + readLine);
            //将键盘输入的消息发送给服务器
            bufferedWriter.write(readLine+"\n");
            bufferedWriter.flush();
            String response = bufferedReader.readLine();
            System.out.println("服务器传过来的消息: " + response);

        }
        bufferedWriter.close();
        inputReader.close();
        clientSocket.close();
    }
}
