package com.ling.mina.server

import android.os.Handler
import com.ling.mina.server.events.ConnectHandler
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.service.IoHandlerAdapter

import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.transport.socket.nio.NioSocketAcceptor

import java.net.InetSocketAddress
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates


/**
 * Created by 任梦林 on 2018/5/21.
 */

class MinaServer : IoHandlerAdapter(){
    private val acceptor: NioSocketAcceptor
    private var isConnected = false
    private var handler: Handler by Delegates.notNull()
    init {
        //创建一个非阻塞的service端的socket
        acceptor = NioSocketAcceptor()
        //设置编解码器  ProtocolCodecFilter拦截器 网络传输需要将对象转换为字节流
        acceptor.filterChain.addLast("codec",
                ProtocolCodecFilter(TextLineCodecFactory()))

        //设置读取数据的缓冲区大小
        acceptor.sessionConfig.readBufferSize = 2048
        //读写通道10秒内无操作进入空闲状态
        acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10)
        handler = Handler()
    }

    fun connect(port: Int): MinaServer {
        if (isConnected)
            return this
        thread {
            try {
                //注册回调 监听和客户端连接状态
                acceptor.handler = this
                acceptor.isReuseAddress = true
                //绑定端口
                acceptor.bind(InetSocketAddress(port))
                isConnected = true
                handler.post {
                    connectCallback?.onOpened()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                handler.post {
                    connectCallback?.onError(e)
                }
                LogUtils.i("服务器连接异常")
                isConnected = false
            }
        }
        return this
    }

    fun sendText(message: String){
        for (client in acceptor.managedSessions.values){
            var ioBuffer = IoBuffer.allocate(message.toByteArray().size)
            ioBuffer.put(message.toByteArray())
            ioBuffer.flip()
            client.write(ioBuffer)
        }
    }

    /**
     * 向客户端发送消息后会调用此方法
     * @param session
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun messageSent(session: IoSession?, message: Any?) {
        super.messageSent(session, message)
        LogUtils.i("服务器发送消息成功")
        connectCallback?.onSendSuccess()
    }

    /**
     * 从端口接受消息，会响应此方法来对消息进行处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun messageReceived(session: IoSession?, message: Any?) {
        super.messageReceived(session, message)
        handler.post {
            connectCallback?.onGetMessage(message)
        }
        val msg = message!!.toString()
        /*if ("exit" == msg) {
            // 如果客户端发来exit，则关闭该连接
            //session.close(true);
            session!!.closeNow()
        }
        // 向客户端发送消息
        val date = Date()
        session!!.write(date)*/
        LogUtils.i("服务器接收消息成功：$msg")
    }

    /**
     * 服务器与客户端创建连接
     * @param session
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession?) {
        super.sessionCreated(session)
        handler.post {
            connectCallback?.onConnected()
        }
        LogUtils.i("服务器与客户端创建连接")
    }

    /**
     * 服务器与客户端连接打开
     * @param session
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession?) {
        super.sessionOpened(session)
        LogUtils.i("服务器与客户端连接打开")
    }

    /**
     * 关闭与客户端的连接时会调用此方法
     * @param session
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession?) {
        super.sessionClosed(session)
        handler.post {
            connectCallback?.onDisConnected()
        }
        LogUtils.i("关闭与客户端的连接时会调用此方法")
    }

    /**
     * 服务器进入空闲状态
     * @param session
     * @param status
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession?, status: IdleStatus?) {
        super.sessionIdle(session, status)
        LogUtils.i("服务器进入空闲状态")
    }

    /**
     * 异常
     * @param session
     * @param cause
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
        super.exceptionCaught(session, cause)
        handler.post {
            connectCallback?.onError(cause)
        }
        LogUtils.i("服务器异常$cause")
    }

    private var connectCallback:ConnectCallback? = null
    fun setConnectCallback(callback:ConnectCallback){
        this.connectCallback = callback
    }
    interface ConnectCallback{
        fun onSendSuccess()
        fun onGetMessage(message: Any?)
        fun onOpened()
        fun onConnected()
        fun onDisConnected()
        fun onError(cause: Throwable)
    }

}
