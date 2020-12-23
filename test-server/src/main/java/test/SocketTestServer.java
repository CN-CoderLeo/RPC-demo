package test;


import rpc.annotation.ServiceScan;
import rpc.tansport.socket.server.SocketServer;

@ServiceScan
public class SocketTestServer {

    public static void main(String[] args) {

        SocketServer socketServer = new SocketServer("127.0.0.1",9001);
        socketServer.start();
    }
}
