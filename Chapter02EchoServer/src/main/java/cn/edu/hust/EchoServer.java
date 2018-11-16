package cn.edu.hust;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }
    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1)
        {
            System.err.println("the Usage:"+EchoServer.class.getSimpleName()+"<port>");
        }
        int port=Integer.valueOf(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        final EchoServerHandler echoServerHandler=new EchoServerHandler();
        //创建EventLoopGroup
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            //创建ServerBootstrap
            final ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() //添加一个EchoServerHandler到子Channel的channelpipeline中
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(echoServerHandler);
                        }
                    });
            //异步地绑定服务器;调用sync()方法阻塞等到绑定完成
            ChannelFuture f=bootstrap.bind().sync();
            //获取Channel的closeFuture并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        }finally {
            //关闭eventLoopGroup并释放所有资源
            eventLoopGroup.shutdownGracefully().sync();
        }

    }
}
