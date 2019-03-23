package com.ling.mina.minaself


import com.ling.mina.minaself.events.ConnectHandler
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.transport.socket.nio.NioSocketConnector

import java.net.InetSocketAddress

/**
 * Created by 任梦林 on 2018/5/21.
 */

class MinaClient : IoHandlerAdapter(){
    private val connector: NioSocketConnector
    private var session: IoSession? = null
    var mConnectHandler: ConnectHandler? = null
    private var isConnected = false
    init {
        connector = NioSocketConnector()
        // 设置链接超时时间
        connector.connectTimeoutMillis = 15000
        // 添加过滤器
        connector.filterChain.addLast("codec",
                ProtocolCodecFilter(TextLineCodecFactory()))
    }

    fun connect(ip: String, port: Int): Boolean {
        if (isConnected)
            return true
        connector.handler = this
        connector.setDefaultRemoteAddress(InetSocketAddress(ip, port))
        // 监听客户端是否断线
        /*connector.addListener(object : IoListener() {
            @Throws(Exception::class)
            override fun sessionDestroyed(arg0: IoSession) {
                // TODO Auto-generated method stub
                super.sessionDestroyed(arg0)
                try {
                    val failCount = 0
                    while (true) {
                        Thread.sleep(3000)
                        println((connector.defaultRemoteAddress as InetSocketAddress).address
                                .hostAddress)
                        val future = connector.connect()
                        println("断线2")
                        future.awaitUninterruptibly()// 等待连接创建完成
                        println("断线3")
                        session = future.session// 获得session
                        println("断线4")
                        if (session != null && session!!.isConnected) {
                            println("断线5")
                            println("断线重连["
                                    + (session!!.remoteAddress as InetSocketAddress).address.hostAddress
                                    + ":" + (session!!.remoteAddress as InetSocketAddress).port + "]成功")
                            session!!.write("start")
                            break
                        } else {
                            println("断线重连失败---->" + failCount + "次")
                        }
                    }
                } catch (e: Exception) {
                    // TODO: handle exception
                    e.printStackTrace()
                }

            }
        })*/
        //开始连接
        try {
            val future = connector.connect()
            future.awaitUninterruptibly()// 等待连接创建完成
            session = future.session// 获得session
            if (session != null && session!!.isConnected) {
                println("开始写数据")
                session!!.write("start")
            } else {
                println("写数据失败")
            }
            isConnected = session != null && session!!.isConnected
            return isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            println("客户端链接异常...")
        }

        return false
    }

    /**
     * 向服务端端发送消息后会调用此方法
     * @param session
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun messageSent(session: IoSession?, message: Any?) {
        super.messageSent(session, message)
        LogUtils.i("客户端发送消息成功")
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
        /*IoBuffer buffer = (IoBuffer) message;
        buffer.order(ByteOrder.BIG_ENDIAN);
        byte arr = buffer.get();
        String msg = message.toString();*/
        LogUtils.i("客户端接收消息成功：")
        connectCallback?.onGetMessage(message)
    }

    /**
     * 服务器与客户端创建连接
     * @param session
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession?) {
        super.sessionCreated(session)
        LogUtils.i("服务器与客户端创建连接")
        connectCallback?.onConnected()
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
        LogUtils.i("关闭与客户端的连接时会调用此方法")
    }

    /**
     * 客户端进入空闲状态
     * @param session
     * @param status
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession?, status: IdleStatus?) {
        super.sessionIdle(session, status)
        LogUtils.i("客户端进入空闲状态")
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
        LogUtils.i("客户端异常$cause")
        connectCallback?.onError(cause)
    }


    private var connectCallback:ConnectCallback? = null
    fun setConnectCallback(callback:ConnectCallback){
        this.connectCallback = callback
    }
    interface ConnectCallback{
        fun onSendSuccess()
        fun onGetMessage(message: Any?)
        fun onConnected()
        fun onError(cause: Throwable)
    }
}
