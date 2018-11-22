package cn.edu.hust.handler;


import cn.edu.hust.domain.SubscribeReq;
import cn.edu.hust.domain.SubscribeResp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SubReqClientHandler extends SimpleChannelInboundHandler<SubscribeResp>{

    protected void channelRead0(ChannelHandlerContext ctx,SubscribeResp msg) throws Exception {
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

    private SubscribeReq subReq(int i) {
        SubscribeReq subscribeReq=new SubscribeReq();
        subscribeReq.setAddress("湖北省武汉市");
        subscribeReq.setPhoneNumber("1242342");
        subscribeReq.setProductName("Netty in Action for  MarShalling");
        subscribeReq.setSubReqID(i);
        subscribeReq.setUserName("Lilinfeng");
        return subscribeReq;
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


