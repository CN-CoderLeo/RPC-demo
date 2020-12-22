package rpc.tansport.socket.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.registry.NacosServiceDiscovery;
import rpc.registry.NacosServiceRegistry;
import rpc.registry.ServiceDiscovery;
import rpc.registry.ServiceRegistry;
import rpc.tansport.RpcClient;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.enumeration.ResponseCode;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;
import rpc.serializer.CommonSerializer;
import rpc.tansport.socket.util.ObjectReader;
import rpc.tansport.socket.util.ObjectWriter;
import rpc.util.RpcMessageChecker;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient implements RpcClient {

    private static final Logger logger=LoggerFactory.getLogger(SocketClient.class);
    private CommonSerializer serializer;
    private ServiceDiscovery serviceDiscovery;

    public SocketClient() {
        this.serviceDiscovery=new NacosServiceDiscovery();

    }

    public Object sendRequest(RpcRequest rpcRequest){

        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);

            return rpcResponse.getData();
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer commonSerializer) {
        this.serializer=commonSerializer;
    }
}
