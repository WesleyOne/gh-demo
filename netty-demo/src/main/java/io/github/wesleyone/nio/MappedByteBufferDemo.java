package io.github.wesleyone.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 可以直接在内存（堆外内存）修改，操作系统不需要拷贝一次
 * @author http://wesleyone.github.io/
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioFileChannelDemo.txt", "rw");
        // 获取对应channel
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1： FileChannel.MapMode.READ_WRITE 代表使用读写模式
         * 参数2：可以直接修改的起始位置
         * 参数3：映射到内存的大小，即将文件的多少个字节映射到内存，可以直接修改的范围就是多少个字节
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'L');

        randomAccessFile.close();

    }
}
