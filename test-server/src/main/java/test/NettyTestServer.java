package test;


import rpc.api.HelloService;
import rpc.tansport.netty.server.NettyServer;


public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1",9000);
        server.publishService(helloService,HelloService.class);

    }
}
