package com.ling.mina.server.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by @author lingxiao on 2018/5/21.
 */

public class TextEncoder extends ProtocolEncoderAdapter{
    @Override
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput output) throws Exception {
        String msg = null;
        if (message instanceof String){
            msg = (String) message;
        }
        if (msg != null){
            CharsetEncoder encoder = (CharsetEncoder) ioSession.getAttribute("encoder");
            if (encoder == null){
                encoder = Charset.defaultCharset().newEncoder();
                ioSession.setAttribute("encoder",encoder);
            }
            IoBuffer ioBuffer = IoBuffer.allocate(msg.length());
            ioBuffer.setAutoExpand(true); //自动扩展容量
            ioBuffer.putString(msg,encoder);
            ioBuffer.flip();

            output.write(ioBuffer);
            output.flush();
        }

    }
}
