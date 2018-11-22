package cn.edu.hust.server;

import cn.edu.hust.handler.HttpFileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import java.net.InetSocketAddress;

public class HttpFileServer {
    private static final String DEFAULT_URL="src/cn/edu/hust/netty/";

    public void run(final String url,final int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        EventLoopGroup workLoopGroup=new NioEventLoopGroup();

            ServerBootstrap bootstrap=new ServerBootstrap();

            bootstrap.group(eventLoopGroup,workLoopGroup)
                    .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());

                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65535));

                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());

                            //支持异步发送大的码流,但不占有过多的内存防止发生Java内存溢出错误
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());

                            ch.pipeline().addLast("fileServerHanlder",new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future=bootstrap.bind().sync();
            future.channel().closeFuture().sync();

            System.out.println("HTTP 文件服务器启动,网址是http://localhost:"+port+"/"+url);
            if(!future.isSuccess())
            {
                eventLoopGroup.shutdownGracefully();
                workLoopGroup.shutdownGracefully();
            }
    }

    public static void main(String[] args) throws InterruptedException {
        int port=8080;

        if(args.length>0)
        {
            port=Integer.valueOf(args[0]);
        }

        String url=DEFAULT_URL;

        if(args.length>1){
            url=args[1];
        }

        new HttpFileServer().run(url,port);
    }
}
