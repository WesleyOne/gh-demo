package io.github.wesleyone.netty.http;

import io.github.wesleyone.netty.simple.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author http://wesleyone.github.io/
 */
public class HttpServer {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel最为服务器通道实现
                    .childHandler(new HttpServerInitializer()); // 设置workGroup的EventLoop的对应处理器
            System.out.println("服务器 is ok...");

            // 绑定端口并同步，生成一个ChannelFuture
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
