package com.kingthy.server;

import com.alibaba.fastjson.JSON;
import com.kingthy.conf.AppConfig;
import com.kingthy.conf.WsConfig;
import com.kingthy.dto.WsMessageDTO;
import com.kingthy.dto.WsResponseCode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;


/**
 * 描述：websocket服务端
 * @author  likejie
 * @date 2017/10/16.
 */
@Component
public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ZkServiceRegister zkServiceRegister;

    public ChannelFuture start() {

        init();

        /**参数配置**/
        String host= WsConfig.NT.getHost();
        int port=WsConfig.NT.getPort();
        boolean isCluster=WsConfig.NT.getIsCluster();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer(channelGroup))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        InetSocketAddress address = StringUtils.isBlank(host) ? new InetSocketAddress(port) : new InetSocketAddress(host, port);
        ChannelFuture channelFuture=bootstrap.bind(address).addListener(futureListener->{
            if(futureListener.isSuccess()){
                LOG.info("-------------服务已启动，端口号："+port+"--------------");

                /**注册netty-websockete服务到zookeeper实现集群及负载均衡**/
                if(isCluster){
                    LOG.info("-------------注册到zookeeper，实现集群部署-------------");
                    zkServiceRegister.registerZookeeper();
                }
            }
        }).syncUninterruptibly();
        channel=channelFuture.channel();
        return channelFuture;
    }
    public void destroy() {
        if(channel != null) {
            channel.close();
        }
        channelGroup.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
    /**
     * 广播
     */
    public void braodcast(String msg){
        WsMessageDTO dto=new WsMessageDTO();
        dto.setContent(msg+"-->当前客户端数量："+channelGroup.size());
        dto.setRequestCode(WsResponseCode.INFO.getValue());
        channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dto)));
    }
    /**
     *
     * 初始化全局配置
     */
    private void init(){
        WsConfig.NT=appConfig.getNetty();
        WsConfig.ZK=appConfig.getZk();
    }

}
