package io.github.wesleyone.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author http://wesleyone.github.io/
 */
public class BioServer {

    public static void main(String[] args) throws Exception {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动监听");
        while (true) {
            System.out.println("等待链接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接上客户端");
            // 获取一个线程，与之通讯
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });

        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程ID：" + Thread.currentThread().getId() + ";线程名称：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            System.out.println("准备读...");
            InputStream inputStream = socket.getInputStream();
            int read = inputStream.read(bytes);
            System.out.println(new String(bytes, 0,read));

            System.out.println("准备写...");
            String message = "线程ID：" + Thread.currentThread().getId() + "为您服务";
            socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
            socket.getOutputStream().flush();
            System.out.println("finish");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
