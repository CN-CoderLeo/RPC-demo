package rpc.tansport;




import rpc.serializer.CommonSerializer;

public interface RpcServer{

    void start();

    void setSerializer(CommonSerializer commonSerializer);

    <T> void publishService(T service,Class<T> serviceClass);
}
