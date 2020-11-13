package testServer;

import rpc.api.HelloService;
import rpc.regisitry.DefaultServiceRegistry;
import rpc.regisitry.ServiceRegistry;
import rpc.socket.server.SocketServer;

public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer rpcServer = new SocketServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
