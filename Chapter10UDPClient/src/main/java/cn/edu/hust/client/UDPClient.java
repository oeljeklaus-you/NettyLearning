package cn.edu.hust.client;

import cn.edu.hust.handler.UDPClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class UDPClient {
    public void run(int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        Bootstrap bootstrap=new Bootstrap();

        bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new UDPClientHandler());
        Channel channel=bootstrap.bind(0).sync().channel();

        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询:", CharsetUtil.UTF_8),new InetSocketAddress("255.255.255.255",port))).sync();

        if(!channel.closeFuture().await(15000))
        {
            System.out.println("查询超时");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1){
            System.err.println("the Usage args <port>");
        }

        new UDPClient().run(Integer.valueOf(args[0]));
    }

}
