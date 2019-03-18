package com.ling.mina.minaself

import com.ling.mina.minaself.coder.TextCodecFactory
import com.ling.mina.minaself.events.ConnectHandler
import com.ling.mina.minaself.events.IoListener

import org.apache.mina.core.future.ConnectFuture
import org.apache.mina.core.service.IoConnector
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import org.apache.mina.transport.socket.nio.NioSocketConnector

import java.net.InetSocketAddress

/**
 * Created by 任梦林 on 2018/5/21.
 */

class MinaClient {
    private val connector: NioSocketConnector
    private var session: IoSession? = null
    var mConnectHandler: ConnectHandler? = null

    init {
        connector = NioSocketConnector()
        // 设置链接超时时间
        connector.connectTimeoutMillis = 15000
        // 添加过滤器
        connector.filterChain.addLast("codec",
                ProtocolCodecFilter(TextLineCodecFactory()))
    }

    fun connect(ip: String, port: Int): Boolean {
        mConnectHandler = ConnectHandler()
        connector.handler = mConnectHandler
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
            return session != null && session!!.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            println("客户端链接异常...")
        }

        return false
    }
}
