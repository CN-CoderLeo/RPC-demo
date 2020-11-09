package rpc.regisitry;


//服务注册通用接口
public interface ServiceRegistry {

    <T> void register(T service);

    Object getService(String serviceName);




}
