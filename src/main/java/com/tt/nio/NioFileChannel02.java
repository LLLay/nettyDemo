package com.tt.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author TongShuo
 * @Date 2021/1/2 16:36
 * @Describe 读取本地文件
 */
public class NioFileChannel02 {
    public static void main(String[] args) throws IOException {

        // 创建文件输入流
        File file = new File("d://file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 获取通道
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据读取到buffer
        fileChannel.read(byteBuffer);

        // 转换读写模式
        byteBuffer.flip();

        /*while (byteBuffer.hasRemaining()){
            System.out.print((char) byteBuffer.get());
        }*/
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }

}
