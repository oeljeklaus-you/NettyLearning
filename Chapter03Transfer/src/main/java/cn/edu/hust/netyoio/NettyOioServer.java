package cn.edu.hust.netyoio;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyOioServer {
    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi\r\n", CharsetUtil.UTF_8));

        EventLoopGroup  eventLoopGroup=new OioEventLoopGroup();

        ServerBootstrap bootstrap=new ServerBootstrap();

        bootstrap.group(eventLoopGroup).channel(OioServerSocketChannel.class)
                .localAddress(9999).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter()
                {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                    }
                });
            }
        });

        ChannelFuture  future=bootstrap.bind().sync();

        future.channel().closeFuture().sync();
    }
}
