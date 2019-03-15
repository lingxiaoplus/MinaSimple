package com.ling.mina.minaself;

import com.ling.mina.minaself.coder.TextCodecFactory;
import com.ling.mina.minaself.events.ConnectHandler;
import com.ling.mina.minaself.events.IoListener;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by 任梦林 on 2018/5/21.
 */

public class MinaClient {
    private NioSocketConnector connector;
    private IoSession session;
    public ConnectHandler mConnectHandler;
    private static final String IP = "192.168.253.3";
    private static final int PORT = 5678;
    public MinaClient(){
        connector = new NioSocketConnector();
        System.out.println(101);
        // 设置链接超时时间
        connector.setConnectTimeoutMillis(15000);
        System.out.println(102);
        // 添加过滤器
        // connector.getFilterChain().addLast("codec", new
        // ProtocolCodecFilter(new CharsetCodecFactory()));
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextCodecFactory()));
        System.out.println(110);
        mConnectHandler = new ConnectHandler();
        connector.setHandler(mConnectHandler);
        System.out.println(111);
        connector.setDefaultRemoteAddress(new InetSocketAddress(IP, PORT));
        // 监听客户端是否断线
        connector.addListener(new IoListener() {
            @Override
            public void sessionDestroyed(IoSession arg0) throws Exception {
                // TODO Auto-generated method stub
                super.sessionDestroyed(arg0);
                try {
                    int failCount = 0;
                    while (true) {
                        Thread.sleep(3000);
                        System.out.println(((InetSocketAddress) connector.getDefaultRemoteAddress()).getAddress()
                                .getHostAddress());
                        ConnectFuture future = connector.connect();
                        System.out.println("断线2");
                        future.awaitUninterruptibly();// 等待连接创建完成
                        System.out.println("断线3");
                        session = future.getSession();// 获得session
                        System.out.println("断线4");
                        if (session != null && session.isConnected()) {
                            System.out.println("断线5");
                            System.out.println("断线重连["
                                    + ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress()
                                    + ":" + ((InetSocketAddress) session.getRemoteAddress()).getPort() + "]成功");
                            session.write("start");
                            break;
                        } else {
                            System.out.println("断线重连失败---->" + failCount + "次");
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });

        /*System.out.println(118);
        if (session != null && session.isConnected()) {
            session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
            System.out.println("客户端断开111111...");
            // connector.dispose();//彻底释放Session,退出程序时调用不需要重连的可以调用这句话，也就是短连接不需要重连。长连接不要调用这句话，注释掉就OK。
        }*/
    }

    public boolean connect(){
        //开始连接
        try {
            System.out.println(112);
            ConnectFuture future = connector.connect();
            System.out.println(113);
            future.awaitUninterruptibly();// 等待连接创建完成
            System.out.println(114);
            session = future.getSession();// 获得session
            System.out.println(115);
            if (session != null && session.isConnected()) {
                System.out.println("开始写数据");
                session.write("start");
            } else {
                System.out.println("写数据失败");
            }
            System.out.println(11);
            return session != null && session.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端链接异常...");
        }
        return false;
    }
}
