package test;


import rpc.api.HelloService;
import rpc.tansport.netty.server.NettyServer;
import rpc.provider.ServiceProviderImpl;
import rpc.provider.ServiceProvider;
import rpc.serializer.ProtobufSerializer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider registry = new ServiceProviderImpl();
        NettyServer server = new NettyServer("127.0.0.1",9000);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService,HelloService.class);

    }
}
