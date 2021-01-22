package com.tt.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author TongShuo
 * @Date 2021/1/2 16:46
 * @Describe 使用Buffer完成文件的读取
 */
public class NioFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true){
            // 一定要复位, 清空Buffer, 将position,limit等等复位
            byteBuffer.clear();

            int read = fileChannel1.read(byteBuffer);
            if (read == -1){
                break;
            }
            // 将buffer中的数据写入输出流
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }
        fileChannel1.transferFrom(fileChannel2, 0, fileChannel2.size());

        fileInputStream.close();
        fileOutputStream.close();

    }
}
