package test;


import rpc.annotation.ServiceScan;
import rpc.tansport.netty.server.NettyServer;

@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {

        NettyServer server = new NettyServer("127.0.0.1",9000);
        server.start();

    }
}
