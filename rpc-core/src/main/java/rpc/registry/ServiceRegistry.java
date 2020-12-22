package rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceRegistry {
    //注册服务
    public void register(String serviceName, InetSocketAddress address);

    //发现服务
    InetSocketAddress lookupService(String serviceName);
}
