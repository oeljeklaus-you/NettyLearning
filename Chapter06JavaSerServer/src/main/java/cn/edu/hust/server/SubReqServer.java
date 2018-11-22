package cn.edu.hust.server;

import cn.edu.hust.handler.SubReqServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class SubReqServer {
    private static void bind(int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        EventLoopGroup workEventLoop=new NioEventLoopGroup();

        try
        {
            ServerBootstrap bootstrap=new ServerBootstrap();

            bootstrap.group(eventLoopGroup,workEventLoop)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //使用weakReferenceMap进行类加载器缓存，内存不足的时候可以释放内存,单个对象设置为1M
                            ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                            ;
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();

        }
        finally {
           eventLoopGroup.shutdownGracefully();
           workEventLoop.shutdownGracefully();

        }


    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1)
        {
            System.err.println("the args must int <port>");
        }

        bind(Integer.valueOf(args[0]));
    }
}
