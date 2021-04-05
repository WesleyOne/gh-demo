package io.github.wesleyone.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author http://wesleyone.github.io/
 */
public class NioFileChannelRead {

    public static void main(String[] args) throws IOException {
        // 创建一个输出流 -> channel
        File file = new File("NioFileChannelDemo.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        // buffer读取channel
        fileChannel.read(byteBuffer);

        // 打印缓存区内容
        System.out.println(new String(byteBuffer.array()));

        // 关闭流
        fileInputStream.close();
    }
}
