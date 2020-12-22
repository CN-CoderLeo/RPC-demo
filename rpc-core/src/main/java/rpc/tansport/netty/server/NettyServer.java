package rpc.tansport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.provider.ServiceProvider;
import rpc.provider.ServiceProviderImpl;
import rpc.registry.NacosServiceRegistry;
import rpc.registry.ServiceRegistry;
import rpc.tansport.RpcServer;
import rpc.codec.CommonDecoder;
import rpc.codec.CommonEncoder;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;
import rpc.serializer.CommonSerializer;

import java.net.InetSocketAddress;


public class NettyServer implements RpcServer  {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private final String host;
    private final int port;

    private final ServiceRegistry serviceRegistry;

    private final ServiceProvider serviceProvider;
    private CommonSerializer serializer;

    public NettyServer(String host,int port){
        this.host=host;
        this.port=port;
        serviceProvider=new ServiceProviderImpl();
        serviceRegistry=new NacosServiceRegistry();
    }

    @Override
    public void start() {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,256)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CommonEncoder(serializer));
//                            pipeline.addLast(new CommonEncoder(new HessianSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }

                    });
            ChannelFuture future = serverBootstrap.bind(host,port).sync();
            future.channel().closeFuture().sync();

        }catch(InterruptedException e){
            logger.error("启动服务器发生错误: ",e);
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void setSerializer(CommonSerializer commonSerializer) {
        serializer=commonSerializer;
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {

        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }
}
