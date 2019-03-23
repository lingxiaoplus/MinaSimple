package com.ling.mina.server

import com.ling.mina.server.events.ConnectHandler
import org.apache.mina.core.buffer.IoBuffer

import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.transport.socket.nio.NioSocketAcceptor

import java.net.InetSocketAddress


/**
 * Created by 任梦林 on 2018/5/21.
 */

class MinaServer {
    var mConnectHandler: ConnectHandler? = null
    private val acceptor: NioSocketAcceptor
    private var isConnected = false

    init {
        //创建一个非阻塞的service端的socket
        acceptor = NioSocketAcceptor()
        //设置编解码器
        acceptor.filterChain.addLast("codec",
                ProtocolCodecFilter(TextLineCodecFactory()))

        //设置读取数据的缓冲区大小
        acceptor.sessionConfig.readBufferSize = 2048
        //读写通道10秒内无操作进入空闲状态
        acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10)

    }

    fun connect(port: Int): Boolean {
        if (isConnected)
            return true
        try {
            mConnectHandler = ConnectHandler()
            //注册回调 监听和客户端连接状态
            acceptor.handler = mConnectHandler
            acceptor.isReuseAddress = true
            //绑定端口
            acceptor.bind(InetSocketAddress(port))
            isConnected = true
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.i("服务器连接异常")
            isConnected = false
            return false
        }
        return true
    }

    fun sendText(message: String){
        for (client in acceptor.managedSessions.values){
            var ioBuffer = IoBuffer.allocate(message.toByteArray().size)
            ioBuffer.put(message.toByteArray())
            ioBuffer.flip()
            client.write(ioBuffer)
        }
    }

}
