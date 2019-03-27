package com.ling.mina.minaself.coder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Created by @author lingxiao on 2018/5/21.
 */

public class TextEncoder extends ProtocolEncoderAdapter{
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput output) throws Exception {
        output.write(o);
        output.flush();
    }
}
