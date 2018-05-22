package com.ling.mina.server.events;

import com.ling.mina.server.LogUtils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;

/**
 * Created by 任梦林 on 2018/5/21.
 */

public class ConnectHandler extends IoHandlerAdapter{
    /**
     * 向客户端发送消息后会调用此方法
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        LogUtils.i("服务器发送消息成功");
    }

    /**
     * 从端口接受消息，会响应此方法来对消息进行处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        String msg = message.toString();
        if ("exit".equals(msg)) {
            // 如果客户端发来exit，则关闭该连接
            //session.close(true);
            session.closeNow();
        }
        // 向客户端发送消息
        Date date = new Date();
        session.write(date);
        LogUtils.i("服务器接收消息成功："+msg);
    }

    /**
     * 服务器与客户端创建连接
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        LogUtils.i("服务器与客户端创建连接");
    }

    /**
     * 服务器与客户端连接打开
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        LogUtils.i("服务器与客户端连接打开");
    }

    /**
     * 关闭与客户端的连接时会调用此方法
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        LogUtils.i("关闭与客户端的连接时会调用此方法");
    }

    /**
     * 服务器进入空闲状态
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        LogUtils.i("服务器进入空闲状态");
    }

    /**
     * 异常
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        LogUtils.i("服务器异常"+cause);
    }
}
