package cn.edu.hust.client;

import cn.edu.hust.handler.SubReqClientHandler;
import cn.edu.hust.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;

public class SubReqClient {
    public static void connect(String host,int port) throws InterruptedException {

        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        //EventLoopGroup workEventLoopGroup=new NioEventLoopGroup();

        try
        {
            Bootstrap  bootstrap=new Bootstrap();

            bootstrap.group(eventLoopGroup).
                    channel(NioSocketChannel.class).
                    option(ChannelOption.TCP_NODELAY,true)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new SubReqClientHandler());
                        }
                    });
            ChannelFuture future=bootstrap.connect().sync();

            future.channel().closeFuture().sync();

        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=2)
        {
            System.err.println("the Usage:</host></port>");
        }
        connect(args[0],Integer.valueOf(args[1]));
    }
}
