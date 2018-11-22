package cn.edu.hust.factory;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public final class MarshallingCodecFactory {

    public static MarshallingDecoder buildMarshallingDecoder() {
        //使用serial创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory=
                Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration=new MarshallingConfiguration();

        configuration.setVersion(5);

        UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallerFactory,configuration);

        MarshallingDecoder marshallingDecoder=new MarshallingDecoder(provider,Integer.MAX_VALUE);
        return marshallingDecoder;
    }


    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory=
                Marshalling.getProvidedMarshallerFactory("serial");

        final MarshallingConfiguration configuration=new MarshallingConfiguration();

        configuration.setVersion(5);

        MarshallerProvider provider=new DefaultMarshallerProvider(marshallerFactory,configuration);

        MarshallingEncoder marshallingEecoder=new MarshallingEncoder(provider);
        return marshallingEecoder;
    }
}
