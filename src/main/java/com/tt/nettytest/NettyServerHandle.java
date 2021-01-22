package com.tt.nettytest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @author TongShuo
 * @Date 2021/1/9 17:14
 * @Describe Netty的handler实现，需要继承netty规定好的某个handlerAdapter,
 *          这时我们自定义一个Handler，才能称为一个Handler
 */
public class NettyServerHandle extends ChannelInboundHandlerAdapter {

    // 读取客户端发送的数据
    /*
    * 1. ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel,地址
    * 2. Object msg: 客户端发送的数据 默认Object
    * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        // 将msg转为一个ByteBuf
        // ByteBuf 是Netty提供的，不是Nio中的ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" +byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());

    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush是write方法和flush方法
        // 将数据写入缓存，并刷新
        // 一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));
    }

    // 处理异常，需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
