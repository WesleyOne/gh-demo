package io.github.wesleyone.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author http://wesleyone.github.io/
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        // 创建 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个Selector
        Selector selector = Selector.open();
        // 服务端监听端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把ServerSocketChannel注册到selector,指定关注事件OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 0表示没有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1s,无连接");
                continue;
            }

            // 如果返回的不是0，就是获取到关注事件相关的selectionKeys集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历selectionKeys,逐个判断事件类型并处理
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                // 获取 SelectionKey
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {
                    // 处理OP_ACCEPT事件
                    // 为该客户端生产一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 将SocketChannel注册到selector,关注事件OP_READ
                    // 给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接："+socketChannel.hashCode());
                }
                if (selectionKey.isReadable()) {
                    // 处理OP_READ事件
                    // 获取channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    // 获取该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    // 将通道数据读到缓存区
                    channel.read(buffer);
                    System.out.println("客户端：" + new String(buffer.array()));
                }

                // 手动从集合中移除 selectionKey，防止重复操作
                keyIterator.remove();
            }


        }


    }
}
