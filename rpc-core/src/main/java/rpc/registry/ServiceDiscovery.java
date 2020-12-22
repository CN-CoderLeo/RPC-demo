package rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    //发现服务
    InetSocketAddress lookupService(String serviceName);
}
