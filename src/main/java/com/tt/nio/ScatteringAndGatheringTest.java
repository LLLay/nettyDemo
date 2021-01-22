package com.tt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author TongShuo
 * @Date 2021/1/3 16:18
 * @Describe
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {
        // 使用ServerSocketChannel 和 SocketChannel 网络

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口 启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(5);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8; // 假定从客户端接收8个字节
        // 循环读取
        while (true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println(byteRead);
                System.out.println();
                // 使用流打印
                Arrays.stream(byteBuffers).map(buffer -> "position"
                        + buffer.position() + ", limit" + buffer.limit()
                ).forEach(System.out::println);
            }
            // 将buffer复位
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;

            }
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead:" + byteRead + " byteWrite:" + byteWrite);
        }

    }
}
