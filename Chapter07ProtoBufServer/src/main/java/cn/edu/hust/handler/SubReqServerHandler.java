package cn.edu.hust.handler;

import cn.edu.hust.domain.SubscribeReq;
import cn.edu.hust.domain.SubscribeResp;
import cn.edu.hust.protobuf.SubscribeReqProto;
import cn.edu.hust.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq subscribeReq=(SubscribeReqProto.SubscribeReq)msg;
        if("Lilinfeng".equalsIgnoreCase(subscribeReq.getUserName()))
        {
            System.out.println("service accept client subscribe req:"+ subscribeReq.toString());
            ctx.writeAndFlush(resp(subscribeReq.getSubReqID()));
        }

    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder=SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespID(0);
        builder.setDesc("Netty Book ordered successed,3 day later.send to the address");
        return  builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
