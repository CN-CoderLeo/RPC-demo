package test;

import rpc.tansport.RpcClient;
import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.tansport.RpcClientProxy;
import rpc.tansport.netty.client.NettyClient;


public class NettyTestClient {
    public static void main(String[] args) {

        RpcClient client = new NettyClient();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
