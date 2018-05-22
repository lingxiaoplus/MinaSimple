package com.ling.mina.minaself.coder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by 任梦林 on 2018/5/21.
 */

public class TextCodecFactory implements ProtocolCodecFactory {
    private TextDecoder decoder;
    private TextEncoder encoder;

    public TextCodecFactory(){
        decoder = new TextDecoder();
        encoder = new TextEncoder();
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
