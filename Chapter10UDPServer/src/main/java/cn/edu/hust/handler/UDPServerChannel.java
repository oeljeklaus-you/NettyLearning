package cn.edu.hust.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

import java.nio.channels.DatagramChannel;

public class UDPServerChannel extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String[] JSON={"一寸光阴一寸金","失败是成功之母","Hello,World!"};
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private String nextQuote()
    {
        int quoteId= ThreadLocalRandom.current().nextInt(JSON.length);
        return JSON[quoteId];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String req=msg.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);

        ctx.writeAndFlush(new
                DatagramPacket(
                        Unpooled.copiedBuffer("谚语的查询结果:"+nextQuote(),CharsetUtil.UTF_8),msg.sender()));
    }
}
