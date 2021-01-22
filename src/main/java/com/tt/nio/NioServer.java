package com.tt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author TongShuo
 * @Date 2021/1/3 17:32
 * @Describe 使用NIO进行网络通信
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个Selector对象
        Selector selector = Selector.open();

        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel注册到Selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true){
            // 等待一秒
            if (selector.select(1000) == 0){// 无事件发生
                System.out.println("服务器等待一秒，无连接");
            }
            // 如果返回的不是0
            // 1. 如果返回大于0，表示已经获取到关注的事件
            // 2. selector.selectedKeys() 获取关注事件的集合
            // 通过selectionKeys获取通道的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历Set
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()){
                // 获取到SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据key对应的通道进行相应的处理
                if (key.isAcceptable()){// 如果是 OP_ACCEPT,有新的客户端连接
                    // 给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 需要设置非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功");
                    // 将socketChannel注册到Selector,关注事件为OP_READ,同时关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if (key.isReadable()){ // 如果发生op_read
                    // 通过Key获取对应的通道
                    SocketChannel channel = (SocketChannel)key.channel();
                    // 获取到该通道关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();

                    channel.read(byteBuffer);

                    System.out.println("客户端发送的信息：" + new String(byteBuffer.array()));
                }

                // 手动从集合移除当前的selectionKey,防止重复操作
                keyIterator.remove();
            }

        }


    }
}
