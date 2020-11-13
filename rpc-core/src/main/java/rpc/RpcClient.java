package rpc;

import rpc.entity.RpcRequest;

public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);
}
