package io.github.wesleyone.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author http://wesleyone.github.io/
 */
public class BioClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1",8888);
        // 写
        System.out.println("准备写...");
        String message = "hello11";
        socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
        socket.getOutputStream().flush();

        // 读
        System.out.println("准备读...");
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        int read = inputStream.read(bytes);
        System.out.println(new String(bytes, 0,read));

        System.out.println("finish");
        socket.close();
    }
}
