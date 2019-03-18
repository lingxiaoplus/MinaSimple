package com.ling.mina.minaself;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args){
        SocketClient socketClient = new SocketClient();
        socketClient.startClient(2333);
    }
    void startClient(int port){
        try {
            Socket clientSocket = new Socket("localhost",port);
            System.out.println("客户端已启动");
            //给服务器发消息
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //接收服务器传过来的消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //键盘输入消息  发送给服务端
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String readLine = null;
            while (!(readLine = inputReader.readLine()).equals("bye")){
                System.out.println("我(客户端)：" + readLine);
                //将键盘输入的消息发送给服务器
                bufferedWriter.write(readLine+"\n");
                bufferedWriter.flush();
                String response = bufferedReader.readLine();
                System.out.println("服务端: " + response);
            }
            bufferedWriter.close();
            inputReader.close();
            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
