package cn.edu.hust;

import cn.edu.hust.protobuf.SubscribeRespProto;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.*;

public class Encoder {
    //使用这个进行编码
    private static byte[] encode(SubscribeRespProto.SubscribeResp resp)
    {
        return resp.toByteArray();
    }

    //解码,使用parseFrom()解析二进制
    private static SubscribeRespProto.SubscribeResp decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeRespProto.SubscribeResp.parseFrom(body);
    }

    private static SubscribeRespProto.SubscribeResp createSubcribeResp()
    {
        //Builder()构建器对SubscribeResp的属性构建
        SubscribeRespProto.SubscribeResp.Builder builder
                =SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(1);
        builder.setRespID(1);
        builder.setDesc("Netty in Action ordered");

        return builder.build();
    }

    public static void main(String[] args) throws IOException {
        SubscribeRespProto.SubscribeResp subscribeResp=createSubcribeResp();

        System.out.println("Before encode:"+subscribeResp.toString());

        System.out.println("Encode byte length:"+encode(subscribeResp).length);

        new ObjectOutputStream(
                new FileOutputStream("/Users/youyujie/Documents/NettyLearning/Chapter07Protobuf/proto/1.dat")).writeObject(subscribeResp);

        SubscribeRespProto.SubscribeResp subscribeResp1=decode(encode(subscribeResp));

        System.out.println("After decode:"+subscribeResp1.toString());

        System.out.println("Assert equal:---->"+subscribeResp.equals(subscribeResp1));
    }
}
