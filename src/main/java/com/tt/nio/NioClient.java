package com.tt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author TongShuo
 * @Date 2021/1/3 17:55
 * @Describe
 */
public class NioClient {

    public static void main(String[] args) throws IOException {

        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不需要阻塞");
            }
        }
        // 如果连接成功
        String str = "hello TongShuo";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
