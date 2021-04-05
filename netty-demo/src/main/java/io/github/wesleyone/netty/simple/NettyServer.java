package io.github.wesleyone.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author http://wesleyone.github.io/
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建BossGroup和WorkGroup
        // 1. bossGroup 仅用于处理OP_ACCEPT事件
        // 2. workGroup 处理OP_READ和OP_WRITE事件以及业务逻辑
        // 3. 两个都是无限循环
        // 默认Group线程数是机器的CPU核数*2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel最为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接的状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道测试对象
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 可以使用一个集合管理<用户表示,SocketChannel>，用于非reactor中的向用户推送消息的场景
                            System.out.println("客户SocketChannel hashcode=" + socketChannel.hashCode());
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 设置workGroup的EventLoop的对应处理器
            System.out.println("服务器 is ok...");

            // 绑定端口并同步，生成一个ChannelFuture
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            // 注册监听器，监控
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口6668成功");
                    } else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
