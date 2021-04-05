package io.github.wesleyone.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @author http://wesleyone.github.io/
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 向pipeline加入处理器

        // 获取pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 加入Netty提供的Http编解码器 HttpServerCodec
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 加入自定义处理器
        pipeline.addLast("MyHttpServerHandler", new HttpServerHandler());
    }
}
