package com.tt.nettyhttp;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author TongShuo
 * @Date 2021/1/14 16:49
 * @Describe
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入一个netty 提供的httpServer code  decode
        // HttpServerCodec是 netty提供的处理http的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 增加一个自定义handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
