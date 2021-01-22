package com.tt.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author TongShuo
 * @Date 2021/1/2 16:24
 * @Describe 创建本地文件
 */
public class NioFileChannel01 {
    public static void main(String[] args) throws IOException {

        String str = "hello";
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d://file01.txt");

        // 通过fileOutputStream 获取对应的FileChannel
        // fileChannel真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将str 放入ByteBuffer
        byteBuffer.put(str.getBytes());
        // 将Buffer转为读取
        byteBuffer.flip();

        // 将数据写入到fileChannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
