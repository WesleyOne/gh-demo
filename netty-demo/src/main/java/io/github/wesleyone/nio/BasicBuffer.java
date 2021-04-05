package io.github.wesleyone.nio;

import java.nio.IntBuffer;

/**
 * @author http://wesleyone.github.io/
 */
public class BasicBuffer {

    public static void main(String[] args) {

        // 创建一个Buffer,大小为5，即可以存5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i=0; i < 4; i++) {
            intBuffer.put(i);
        }

        // 读写切换
        intBuffer.flip();

        // 读取数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        System.out.println("finish1");

        // 重置坐标
        intBuffer.clear();

        // 写
        for (int i=0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }
        // 读写切换
        intBuffer.flip();
        // 读取数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        System.out.println("finish2");
    }
}
