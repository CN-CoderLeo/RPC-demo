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


    @Override
    public void register(String serviceName, InetSocketAddress address) {
        try{
            NacosUtil.registerService(serviceName,address);
        }catch(NacosException e){
            logger.error("注册服务时发生错误：", e);
        }
    }


}

