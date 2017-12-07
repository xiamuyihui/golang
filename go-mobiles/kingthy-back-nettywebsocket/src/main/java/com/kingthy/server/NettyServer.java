package com.kingthy.server;

import com.alibaba.fastjson.JSON;
import com.kingthy.conf.NConfg;
import com.kingthy.dto.WSMessageDTO;
import com.kingthy.dto.WSResponseCode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 *  netty websocket服务端
 * Created by likejie on 2017/10/16.
 */
public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();


    public NettyServer(){

    }
    public void start() {
        try {
            /**参数配置**/
            String host=NConfg.server.host;
            int port=NConfg.server.port;
            boolean isCluster=NConfg.server.isCluster;

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(channelGroup))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);



            InetSocketAddress address = StringUtils.isBlank(host) ? new InetSocketAddress(port) : new InetSocketAddress(host, port);
            Channel channel=bootstrap.bind(address).addListener(future->{
                if(future.isSuccess()){
                    LOG.info("-------------服务已启动，端口号："+port+"--------------");
                }
            }).channel();
            channel.closeFuture().sync();//异步关闭
        } catch (Exception ex) {
            LOG.error(ex.toString());
        }finally {
            LOG.info("-------------服务关闭--------------");
            channelGroup.close();
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    /**
     * 广播
     */
    public void braodcast(String msg){
        WSMessageDTO dto=new WSMessageDTO();
        dto.setContent(msg+"-->当前客户端数量："+channelGroup.size());
        dto.setRequestCode(WSResponseCode.INFO.getValue());
        channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dto)));
    }
    /**
     * 只获取第一块网卡绑定的ip地址
     *
     * @param getLocal 局域网IP
     * @return ip
     */
    public static String getInetAddress(boolean getLocal) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress()) continue;
                    if (address.getHostAddress().contains(":")) continue;
                    if (getLocal) {
                        if (address.isSiteLocalAddress()) {
                            return address.getHostAddress();
                        }
                    } else {
                        if (!address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
            LOG.debug("getInetAddress is null, getLocal={}", getLocal);
            return getLocal ? "127.0.0.1" : null;
        } catch (Throwable e) {
            LOG.error("getInetAddress exception", e);
            return getLocal ? "127.0.0.1" : null;
        }
    }
}
