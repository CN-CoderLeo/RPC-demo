package test;

import rpc.api.HelloService;
import rpc.tansport.socket.server.SocketServer;

public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1",9001);

        socketServer.publishService(helloService,HelloService.class);
    }
}
