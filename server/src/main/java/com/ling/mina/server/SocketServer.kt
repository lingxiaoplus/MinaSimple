package com.ling.mina.server

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket


fun main(args: Array<String>) {
    var socketServer = SocketServer()
    socketServer.startServer(2333)
}
class SocketServer {

    fun startServer(port: Int) {
        val serverSocket = ServerSocket(port)
        System.out.println("服务器已启动,等待客户连接...")
        //调用accept()方法开始监听，等待客户端的连接 这个方法会阻塞当前线程
        val socket = serverSocket.accept()
        System.out.println("客户端连接成功")

        var bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()!!))
        var bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()!!))
        var inputReader = BufferedReader(InputStreamReader(System.`in`))

        var receivedMsg = bufferedReader.readLine()
        while (receivedMsg != null){
            println("收到客户端的消息：" + receivedMsg)
            bufferedWriter.write("我是服务器，我收到消息了：" + socket.localAddress.hostAddress + "\n")
            bufferedWriter.flush()
        }
        socket.close()
        serverSocket.close()
        bufferedWriter.close()
        bufferedReader.close()
    }
}