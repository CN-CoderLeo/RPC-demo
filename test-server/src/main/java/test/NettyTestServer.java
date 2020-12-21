package test;


import rpc.api.HelloService;
import rpc.netty.server.NettyServer;
import rpc.regisitry.DefaultServiceRegistry;
import rpc.regisitry.ServiceRegistry;
import rpc.serializer.KryoSerializer;
import rpc.serializer.ProtobufSerializer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new ProtobufSerializer());
        server.start(9999);

    }
}
