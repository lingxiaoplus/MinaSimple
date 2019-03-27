package com.ling.mina.minaself.coder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.ByteOrder;

/**
 * Created by @author lingxiao on 2018/5/21.
 */

public class TextDecoder extends CumulativeProtocolDecoder{

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput output) throws Exception {
        /*if (in.remaining() >= 8) {//前8字节是包头
            in.mark();
            in.order(ByteOrder.LITTLE_ENDIAN);
            @SuppressWarnings("unused")
            int type = in.getInt();
            int len = in.getInt();
            //注意上面的get操作会导致下面的remaining()值发生变化
            if (in.remaining() < len) {
                //如果消息内容不够，则重置恢复position位置到操作前,进入下一轮, 接收新数据，以拼凑成完整数据
                // 拆包的情况
                in.reset();
                return false;
            } else {
                //消息内容足够
                in.reset();
                int totalLen = (int) (8 + len);

                byte[] packArr = new byte[totalLen];
                in.get(packArr, 0, totalLen);

                IoBuffer buffer = IoBuffer.allocate(totalLen);
                buffer.put(packArr);
                buffer.flip();
                output.write(buffer);
                buffer.free();

                if (in.remaining() > 0) {//如果读取一个完整包内容后还粘了包，就让父类再调用一次，进行下一次解析
                    // 粘包了
                    return true;
                }
            }
        }*/
        //处理成功了，让父类继续处理下一个包
        return false;
    }
}
