package rpc.tansport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.handler.RequestHandler;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.provider.ServiceProvider;
import rpc.registry.ServiceRegistry;
import rpc.serializer.CommonSerializer;
import rpc.tansport.socket.util.ObjectReader;
import rpc.tansport.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;

public class RequestHandlerThread implements  Runnable {


    private static final Logger logger= LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler,  ServiceRegistry serviceRegistry, CommonSerializer commonSerializer){
        this.socket=socket;
        this.requestHandler=requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer=commonSerializer;
    }

    @Override
    public void run() {

        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            String interfaceName = rpcRequest.getInterfaceName();
            Object result = requestHandler.handle(rpcRequest);
            RpcResponse<Object> response = RpcResponse.success(result,rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }

    }


}
