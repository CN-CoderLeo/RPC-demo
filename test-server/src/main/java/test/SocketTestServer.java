package test;

import rpc.api.HelloService;
import rpc.regisitry.DefaultServiceRegistry;
import rpc.regisitry.ServiceRegistry;
import rpc.serializer.HessianSerializer;
import rpc.socket.server.SocketServer;
import test.HelloServiceImpl;

public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.setSerializer(new HessianSerializer());
        socketServer.start(9000);
    }
}
