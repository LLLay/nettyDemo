package com.tt.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TongShuo
 * @Date 2020/12/31 15:35
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建线程池
        ExecutorService threadPoolService = Executors.newCachedThreadPool();
        // 2. 创建Socket
        ServerSocket serverSocket = new ServerSocket(6666);

        while (true){
            // 监听
            final Socket socket = serverSocket.accept();

            // 创建线程进行通信
            threadPoolService.execute(new Runnable() {
                public void run() {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    // 编写一个handler方法，和客户端通信
    public static void handler(Socket socket) throws IOException {
        try {
            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true){
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes, 0, read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
