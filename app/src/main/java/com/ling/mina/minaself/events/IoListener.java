package com.ling.mina.minaself.events;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by 任梦林 on 2018/5/21.
 */

public class IoListener implements IoServiceListener {
    @Override
    public void serviceActivated(IoService ioService) throws Exception {

    }

    @Override
    public void serviceIdle(IoService ioService, IdleStatus idleStatus) throws Exception {

    }

    @Override
    public void serviceDeactivated(IoService ioService) throws Exception {

    }

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionDestroyed(IoSession ioSession) throws Exception {

    }
}
