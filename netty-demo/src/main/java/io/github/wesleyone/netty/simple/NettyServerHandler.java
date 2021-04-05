package io.github.wesleyone.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler
 * 需要继承Netty的规定的某个HandlerAdapter
 * @author http://wesleyone.github.io/
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据
     * @param ctx   上下文，可以拿到pipeline\channel
     * @param msg   接收到的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 直接处理
//        System.out.println("服务器处理线程：" + Thread.currentThread().getName());
//        System.out.println("ctx = " + ctx);
//        // 将msg转成 Netty提供的ByteBuf，不是NIO的ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("收到消息：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("消息来源：" + ctx.channel().remoteAddress());

//        // 用户自定义【普通任务】，execute 任务交由taskQueue处理，多个任务是同步运行的
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead taskQueue 5秒", CharsetUtil.UTF_8));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead taskQueue 8秒", CharsetUtil.UTF_8));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
        // 用户自定义【定时】任务，schedule 任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead schedule 5秒", CharsetUtil.UTF_8));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);



    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写到缓存并刷新到管道channel
        // 对发送的数据编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，我是服务端，channelReadComplete", CharsetUtil.UTF_8));
    }

    /**
     * 发生异常，一般需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
