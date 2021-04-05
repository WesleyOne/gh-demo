package io.github.wesleyone.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author http://wesleyone.github.io/
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param channelHandlerContext 上下文对象
     * @param in   入站的bytebuf
     * @param out      将解码后的数据传给下一个处理器
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
