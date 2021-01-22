package com.tt.nettytest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @author TongShuo
 * @Date 2021/1/9 17:43
 * @Describe
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务器", CharsetUtil.UTF_8));
    }

    // 当通道有读取事件时，则触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址:" + ctx.channel().remoteAddress());
    }

}
