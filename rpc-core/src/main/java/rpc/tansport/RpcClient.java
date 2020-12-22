package rpc.tansport;

import rpc.entity.RpcRequest;
import rpc.serializer.CommonSerializer;

public interface RpcClient {
    //默认序列化器为Kryo
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);


}
