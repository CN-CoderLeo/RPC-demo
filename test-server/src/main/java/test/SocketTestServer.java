package test;

import rpc.api.HelloService;
import rpc.provider.ServiceProviderImpl;
import rpc.provider.ServiceProvider;
import rpc.serializer.HessianSerializer;
import rpc.tansport.socket.server.SocketServer;

public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider serviceProvider = new ServiceProviderImpl();
        SocketServer socketServer = new SocketServer("127.0.0.1",9001);
        socketServer.setSerializer(new HessianSerializer());
        socketServer.publishService(helloService,HelloService.class);
    }
}
