package test;

import rpc.RpcClientProxy;
import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.serializer.HessianSerializer;
import rpc.serializer.KryoSerializer;
import rpc.socket.client.SocketClient;

public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }


}
