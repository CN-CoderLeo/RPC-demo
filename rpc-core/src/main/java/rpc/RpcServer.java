package rpc;


//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;

import rpc.serializer.CommonSerializer;

public interface RpcServer    {




    void start(int port);

    void setSerializer(CommonSerializer commonSerializer);
}
