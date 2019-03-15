package com.ling.mina.server;

import android.graphics.Camera;

import com.ling.mina.server.coder.TextCodecFactory;
import com.ling.mina.server.events.ConnectHandler;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

/**
 * Created by 任梦林 on 2018/5/21.
 */

public class MinaServer {
    private static int PORT = 5678;
    public ConnectHandler mConnectHandler;
    public MinaServer(){
        NioSocketAcceptor acceptor = null;
        try {
            //创建一个非阻塞的service端的socket
            acceptor = new NioSocketAcceptor();
            //设置过滤器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new TextCodecFactory()));
            //设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            //读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,15);
            mConnectHandler = new ConnectHandler();
            //注册回调
            acceptor.setHandler(mConnectHandler);
            acceptor.setReuseAddress(true);
            //绑定端口
            acceptor.bind(new InetSocketAddress(PORT));
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.i("服务器启动异常");
        }
    }
}
