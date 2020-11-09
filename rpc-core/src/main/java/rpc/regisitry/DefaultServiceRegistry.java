package rpc.regisitry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceRegistry implements  ServiceRegistry {

    private static final Logger logger= LoggerFactory.getLogger(DefaultServiceRegistry.class);



    private final Map<String , Object> serviceMap=new ConcurrentHashMap<>();
    private final Set<String> registerService=ConcurrentHashMap.newKeySet();
    @Override
    public  synchronized <T> void register(T service) {
        String serviceName=service.getClass().getCanonicalName();
        if(registerService.contains(serviceName)){
            return;
        }
        registerService.add(serviceName);
        Class<?>[] interfaces=service.getClass().getInterfaces();
        if(interfaces.length==0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i:interfaces){
            serviceMap.put(i.getCanonicalName(),service);

        }
        logger.info("向接口：{} 注册服务：{}",interfaces,serviceName);

    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service =serviceMap.get(serviceName);
        if(service==null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;

    }
}
