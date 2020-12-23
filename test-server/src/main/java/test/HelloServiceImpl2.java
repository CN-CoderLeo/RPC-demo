package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.annotation.Service;
import rpc.api.HelloObject;
import rpc.api.HelloService;


public class HelloServiceImpl2 implements HelloService {

    private static final Logger logger= LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}",object.getMessage());
        return "来自socket服务端 这是调用的返回值 id=" +object.getId();
    }
}
