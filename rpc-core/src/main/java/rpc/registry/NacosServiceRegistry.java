package rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;
import java.net.InetSocketAddress;
import java.util.List;

public class NacosServiceRegistry  implements ServiceRegistry{

    private static final Logger logger= LoggerFactory.getLogger(NacosServiceRegistry.class);
    private static final String SERVER_ADDR="127.0.0.1:8848";
    private static final NamingService namingService;


    static{

        try{
            namingService= NamingFactory.createNamingService(SERVER_ADDR);
        }catch(NacosException e){
            logger.error("连接到Nacos时有错误发生: " ,e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress address) {
        try{
            namingService.registerInstance(serviceName,address.getHostName(),address.getPort());

        }catch(NacosException e){
            logger.error("注册服务时发生错误：", e);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try{
            List<Instance> instances=namingService.getAllInstances(serviceName);
            Instance instance=instances.get(0);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        }catch(NacosException e){
            logger.error("获取服务发生错误：" ,e);
        }
        return null;
    }
}

