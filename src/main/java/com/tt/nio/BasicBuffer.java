package com.tt.nio;

import java.nio.IntBuffer;

/**
 * @author TongShuo
 * @Date 2021/1/2 15:01
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明Buffer
        // 创建buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++){
            intBuffer.put(2 * i);
        }

        // 从buffer读取数据
        // 将buffer转换，读写转换
        intBuffer.flip();
        System.out.println(intBuffer.get());
        intBuffer.flip();
        intBuffer.put(100);
        intBuffer.flip();
        // 读取
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
