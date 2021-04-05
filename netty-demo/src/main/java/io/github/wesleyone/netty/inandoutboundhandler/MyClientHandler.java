package io.github.wesleyone.netty.inandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author http://wesleyone.github.io/
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从服务端" + ctx.channel().remoteAddress() + "读取到long：" + msg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");

//        ctx.writeAndFlush(123456L);

        // 以下两种，写入的是ByteBuf类型，和自定义的编码器泛型Long不一致，所以在源码write中不会使用自定义编码器，直接返回数据
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdefghijkmnopq", CharsetUtil.UTF_8));
//        ctx.writeAndFlush(Unpooled.copyLong(654321L));
    }
}
