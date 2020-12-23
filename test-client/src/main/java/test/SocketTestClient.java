package test;

import rpc.tansport.RpcClientProxy;
import rpc.api.HelloObject;
import rpc.api.HelloService;


import rpc.tansport.socket.client.SocketClient;

public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        for(int i = 0; i < 20; i ++) {
            String res = helloService.hello(object);
            System.out.println(res);
        }
    }


}
