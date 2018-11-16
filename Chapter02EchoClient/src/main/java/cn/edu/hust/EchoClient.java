package cn.edu.hust;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final int port;

    public EchoClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1)
        {
            System.err.println("the Usage:"+EchoClient.class.getSimpleName()+"<port>");
        }
        int port=Integer.valueOf(args[0]);
        new EchoClient(port).start();
    }

    private void start() throws InterruptedException {
        final EchoClientHandler echoClientHandler=new EchoClientHandler();
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try
        {
            final Bootstrap  bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoClientHandler);
                        }

                    });
            //连接到远程节点，阻塞等待直到连接完成。
            ChannelFuture channelFuture=bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally
        {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
