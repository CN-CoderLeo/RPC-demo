package rpc.tansport.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.provider.ServiceProviderImpl;
import rpc.provider.ServiceProvider;
import rpc.handler.RequestHandler;
import rpc.util.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger= LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static final String THREAD_NAME_PREFIX = "netty-server-handler";
    private static ServiceProvider serviceProvider;
    private static final ExecutorService threadPool;


    static{
        requestHandler=new RequestHandler();
        serviceProvider =new ServiceProviderImpl();
        threadPool= ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        threadPool.execute(() -> {
            try {
                logger.info("服务器接收到请求: {}", msg);
                Object result = requestHandler.handle(msg);
                ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
                future.addListener(ChannelFutureListener.CLOSE);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        });
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
    {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

}
