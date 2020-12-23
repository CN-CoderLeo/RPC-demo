package rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;


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

