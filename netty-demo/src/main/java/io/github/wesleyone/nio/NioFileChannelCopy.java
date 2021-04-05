package io.github.wesleyone.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 拷贝
 * 输入流读 -》channel读 -》 buffer缓存区 -》 channel写 -》输出流写
 * @author http://wesleyone.github.io/
 */
public class NioFileChannelCopy {

    public static void main(String[] args) throws IOException {

        // 先读

        // 创建一个输出流 -> channel
        FileInputStream fileInputStream = new FileInputStream("NioFileChannelDemo.txt");
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("NioFileChannelDemoCopy.txt");
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileOutChannel = fileOutputStream.getChannel();

        // 创建一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);

        while (true) {
            // 重要：重置缓存区位置
            byteBuffer.clear();
            // buffer读取channel
            int read = fileChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            // 将buffer写入channel
            fileOutChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
