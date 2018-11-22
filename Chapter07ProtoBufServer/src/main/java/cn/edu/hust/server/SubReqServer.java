package cn.edu.hust.server;

import cn.edu.hust.handler.SubReqServerHandler;
import cn.edu.hust.protobuf.SubscribeReqProto;
import cn.edu.hust.protobuf.SubscribeRespProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
                            //主要用于半包处理
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //实际上要告诉解码目标类
                            ch.pipeline().addLast(
                                    new ProtobufDecoder(
                                           SubscribeReqProto.SubscribeReq.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
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
