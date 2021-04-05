package io.github.wesleyone.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 将数据写入到buffer时，可以采用buffer数组，依次写入[分散]
 * Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
 * @author http://wesleyone.github.io/
 */
public class ScatteringAndGatheringDemo {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到Socket,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端链接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //假定客户端消息8个字节
        int messageLen = 8;
        while (true) {
            long byteRead = 0;
            while (byteRead < messageLen) {
                long read = socketChannel.read(byteBuffers);
                // 累计读取的字节数
                byteRead += read;
                System.out.println("byteRead:" + byteRead);
                // 使用流打印，看看当前limit和position
                Arrays.stream(byteBuffers)
                        .map(buffer -> "pos="+buffer.position()+",limit="+buffer.limit())
                        .forEach(System.out::println);
            }

            // 将所有的buffer进行flip
            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            long byteWrite = 0;
            while (byteWrite < messageLen) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
                System.out.println("byteWrite:" + byteWrite);
            }

            // 将所有buffer进行clear
            Arrays.stream(byteBuffers).forEach(Buffer::clear);

            socketChannel.close();
            serverSocketChannel.close();
        }

    }
}
