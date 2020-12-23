package rpc.tansport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.handler.RequestHandler;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.serializer.CommonSerializer;
import rpc.tansport.socket.util.ObjectReader;
import rpc.tansport.socket.util.ObjectWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketRequestHandlerThread implements  Runnable {


    private static final Logger logger= LoggerFactory.getLogger(SocketRequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;

    private CommonSerializer serializer;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer commonSerializer){
        this.socket=socket;
        this.requestHandler=requestHandler;
        this.serializer=commonSerializer;
    }

    @Override
    public void run() {

        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
             RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
             Object result = requestHandler.handle(rpcRequest);
             RpcResponse<Object> response = RpcResponse.success(result,rpcRequest.getRequestId());
             ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }

    }


}
