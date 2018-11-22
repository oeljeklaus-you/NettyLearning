package cn.edu.hust.handler;


import cn.edu.hust.domain.SubscribeReq;
import cn.edu.hust.domain.SubscribeResp;
import cn.edu.hust.protobuf.SubscribeReqProto;
import cn.edu.hust.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SubReqClientHandler extends SimpleChannelInboundHandler<SubscribeRespProto.SubscribeResp>{

    protected void channelRead0(ChannelHandlerContext ctx,SubscribeRespProto.SubscribeResp msg) throws Exception {
        System.out.println("Recevied from Server response:"+msg.toString());
    }

    // 将10次的请求一次发送防止拆包和粘包
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++)
        {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setAddress("湖北省武汉市");
        builder.setPhoneNumber("1242342");
        builder.setProductName("Netty in Action");
        builder.setSubReqID(i);
        builder.setUserName("Lilinfeng");
        return builder.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}


