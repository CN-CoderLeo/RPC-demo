package rpc;

import rpc.entity.RpcRequest;
import rpc.serializer.CommonSerializer;

public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer commonSerializer);
}
