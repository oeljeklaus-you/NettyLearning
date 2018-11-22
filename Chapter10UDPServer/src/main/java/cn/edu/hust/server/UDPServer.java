package cn.edu.hust.server;

import cn.edu.hust.handler.UDPServerChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class UDPServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();

        bootstrap.group(group).channel(NioDatagramChannel.class)//这里设置广播，UDP不不存在服务端和客户端连接
                .localAddress(new InetSocketAddress(port)).option(ChannelOption.SO_BROADCAST,true)
                .handler(new UDPServerChannel());
        ChannelFuture future=bootstrap.bind().sync();
        future.channel().closeFuture().await();
        if(!future.isSuccess())
        {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1){
            System.err.println("the Usage args <port>");
        }

        new UDPServer().run(Integer.valueOf(args[0]));
    }
}
