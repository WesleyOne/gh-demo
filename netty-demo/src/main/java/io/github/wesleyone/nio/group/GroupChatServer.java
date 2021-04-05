package io.github.wesleyone.nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author http://wesleyone.github.io/
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 8889;

    public GroupChatServer() {
        try {
            // 获取选择器
            selector = Selector.open();
            // 获取服务端channel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 非阻塞
            listenChannel.configureBlocking(false);
            // 将channel注册到选择器
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count <= 0) {
                    System.out.println("等待中...");
                    continue;
                }

                // 有事件处理
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // OP_ACCEPT事件
                    if (selectionKey.isAcceptable()) {
                        // 新建SocketChannel并注册到选择器
                        SocketChannel socketChannel = listenChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + " 上线");
                    }
                    // OP_READ事件
                    if (selectionKey.isReadable()) {
                        readData(selectionKey);
                    }

                    // 删除当前key，防止重复处理
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) {

        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0) {
                // 读取到数据
                String message = new String(buffer.array());
                System.out.println(channel.getRemoteAddress() + ":" + message);
                // 向其他客户端转发消息
                sendInfo2OtherClients(message, channel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户端
     * @param message   消息内容
     * @param fromChannel   排除自己
     */
    private void sendInfo2OtherClients(String message, SocketChannel fromChannel) throws IOException {
        System.out.println("服务器转发消息。。。");
        for(SelectionKey key: selector.keys()) {
            // 通过key获取channel
            SelectableChannel channel = key.channel();
            if (!(channel instanceof  SocketChannel)) {
                continue;
            }
            if (channel == fromChannel) {
                // 排除自己
                continue;
            }
            SocketChannel socketChannel = (SocketChannel) channel;
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);
        }
    }
}
