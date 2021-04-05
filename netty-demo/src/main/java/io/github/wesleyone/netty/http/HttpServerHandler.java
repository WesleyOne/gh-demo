package io.github.wesleyone.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 * @author http://wesleyone.github.io/
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        // 判断msg是不是httprequest请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("httpObject 类型=" + httpObject.getClass());
            System.out.println("客户端地址=" + channelHandlerContext.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest)httpObject;
            // 获取uri
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("静态资源不响应");
                return;
            }

            // 回复浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            // 构造一个http响应,即 httpresponse
            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 将构建好的response返回
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
