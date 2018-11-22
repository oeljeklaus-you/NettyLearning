package cn.edu.hust.handler;

import cn.edu.hust.domain.SubscribeReq;
import cn.edu.hust.domain.SubscribeResp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq subscribeReq=(SubscribeReq)msg;
        if("Lilinfeng".equalsIgnoreCase(subscribeReq.getUserName()))
        {
            System.out.println("service accept client subscribe req:"+ subscribeReq.toString());
            ctx.writeAndFlush(resp(subscribeReq.getSubReqID()));
        }

    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp subscribeResp=new
                SubscribeResp();
        subscribeResp.setSubReqID(subReqID);
        subscribeResp.setRespID(0);
        subscribeResp.setDesc("Netty Book ordered successed,3 day later.send to the address");
        return  subscribeResp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
