package io.github.wesleyone.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 拷贝
 * @author http://wesleyone.github.io/
 */
public class NioFileChannelTransferFrom {

    public static void main(String[] args) throws IOException {
        // 创建一个输出流 -> channel
        FileInputStream fileInputStream = new FileInputStream("NioFileChannelDemo.txt");
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("NioFileChannelDemoTrans.txt");
        // 通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileOutChannel = fileOutputStream.getChannel();

        fileOutChannel.transferFrom(fileChannel, 0, fileChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
