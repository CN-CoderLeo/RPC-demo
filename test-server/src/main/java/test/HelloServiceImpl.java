package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.annotation.Service;
import rpc.api.HelloObject;
import rpc.api.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger logger= LoggerFactory.getLogger(HelloServiceImpl.class);
    public String hello(HelloObject object) {
        logger.info("接收到：{}",object.getMessage());
        return "来自netty服务端 这是调用的返回值 id=" +object.getId();
    }
}
