package io.github.wesleyone.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 写文件
 * 数据 -> buffer缓存区 -> channel -> output stream
 * @author http://wesleyone.github.io/
 */
public class NioFileChannelWrite {

    public static void main(String[] args) throws IOException {

        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("NioFileChannelDemo.txt");
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将数据写入buffer
        String message = "hello";
        byteBuffer.put(message.getBytes());

        // 读写切换
        byteBuffer.flip();

        // 将buffer写入channel
        fileChannel.write(byteBuffer);

        // 关闭流
        fileOutputStream.close();

    }
}
