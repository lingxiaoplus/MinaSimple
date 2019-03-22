package com.ling.mina.minaself.events

import com.ling.mina.minaself.LogUtils

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession

import java.nio.ByteOrder
import java.util.Date

/**
 * Created by 任梦林 on 2018/5/21.
 */

class ConnectHandler : IoHandlerAdapter() {
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
    }

}
