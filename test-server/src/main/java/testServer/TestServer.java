package testServer;

import rpc.api.HelloService;
import rpc.regisitry.DefaultServiceRegistry;
import rpc.regisitry.ServiceRegistry;
import rpc.server.RpcServer;

public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
