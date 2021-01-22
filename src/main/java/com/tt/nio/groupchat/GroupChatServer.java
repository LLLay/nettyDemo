package com.tt.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author TongShuo
 * @Date 2021/1/4 16:29
 * @Describe
 */
public class GroupChatServer {

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    private Selector selector;
    private ServerSocketChannel listenSocketChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer(){
        try {
            // 得到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            listenSocketChannel.socket().bind(new InetSocketAddress(PORT));
            listenSocketChannel.configureBlocking(false);

            // 将listenServerChannel注册到selector
            listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 监听
    public void listen(){
        try {
            // 循环处理
            while (true){
                int count = selector.select();
                if (count > 0){
                    // 遍历SelectorKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        // 取出key
                        SelectionKey key = iterator.next();
                        // 监听accept
                        if (key.isAcceptable()){
                            //
                            SocketChannel socketChannel = listenSocketChannel.accept();
                            socketChannel.configureBlocking(false);

                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress()+"上线了");
                        }
                        if (key.isReadable()){// 通道发送read事件，即通道是可读
                            // 处理读
                            readDate(key);
                        }

                        // 删除当前key
                        iterator.remove();
                    }
                }else {
                    System.out.println("等待连接....");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    // 处理客户端消息
    public void readDate(SelectionKey key){
        SocketChannel socketChannel = null;
        try {
            // 得到channel
            socketChannel = (SocketChannel) key.channel();
            // 创建buffer,存储读取的值
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            if (read > 0){
                String s = new String(byteBuffer.array());
                System.out.println("form 客户端：" + s);

                // 向其他客户端转发消息
                sendInformationClient(s, socketChannel);
            }

        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线");
                // 取消注册
                key.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // 转发消息给其他客户端
    private void sendInformationClient(String s, SocketChannel socketChannel) throws IOException {
        System.out.println("服务器转发消息");
        // 遍历所有注册到selector的channel，并排除自己
        for (SelectionKey key: selector.keys()){
            // 通过key取出对应的channel
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将s 存在buffer
                ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
                // 将buffer的数据写入通道
                dest.write(buffer);
            }
        }

    }
}
