package test;

import rpc.api.ByeService;
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
        String res = helloService.hello(object);
        System.out.println(res);

        ByeService byeService=proxy.getProxy(ByeService.class);
        String result=byeService.bye("socket client");
        System.out.println(result);



    }


}
