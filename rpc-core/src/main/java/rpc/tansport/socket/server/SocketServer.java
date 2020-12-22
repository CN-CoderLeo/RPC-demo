package rpc.tansport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.handler.RequestHandler;
import rpc.provider.ServiceProviderImpl;
import rpc.registry.NacosServiceRegistry;
import rpc.registry.ServiceRegistry;
import rpc.tansport.RpcServer;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;
import rpc.provider.ServiceProvider;
import rpc.serializer.CommonSerializer;
import rpc.util.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);


    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceProvider serviceProvider;
    private CommonSerializer serializer;
    private String host;
    private int port;
    private final ServiceRegistry serviceRegistry;


    public SocketServer(String host,int port) {
        this.host=host;
        this.port=port;
        this.serviceProvider = new ServiceProviderImpl();
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        serviceRegistry=new NacosServiceRegistry();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer commonSerializer) {
        this.serializer=commonSerializer;
    }


    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service,serviceClass);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }
}
