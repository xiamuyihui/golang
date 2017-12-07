/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.kingthy.wsclient;


import com.alibaba.fastjson.JSON;
import com.kingthy.conf.AppConfig;
import com.kingthy.dto.WsMessageDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 描述：websocket客户端
 * @author likejie
 * @date
 */
public  class WebSocketClient {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketClient.class);
    private static final EventLoopGroup GROUP = new NioEventLoopGroup();
    private static final  String WS="ws";
    private static final  String WSS="wss";
    private static final  String HOST="27.0.0.1";

    private Channel channel;
    private Bootstrap bootstrap;
    @Autowired
    private AppConfig  appConfig;

    public WebSocketClient(){

    }
    /**
     *
     * 建立连接
     */
    public void connect() {
        try {
            final String wsAddress =appConfig.getRemoteAddress();
            if(StringUtils.isBlank(wsAddress)){
                LOG.error("连接地址不正确，未能连接到远程服务器！");
                return;
            }
            URI uri = new URI(wsAddress);
            String scheme = uri.getScheme() == null ? WS : uri.getScheme();
            final String host = uri.getHost() == null ? HOST : uri.getHost();
            final int port=uri.getPort()==-1?9090:uri.getPort();

            final boolean ssl = WSS.equalsIgnoreCase(scheme);
            final SslContext sslCtx;
            if (ssl) {
                sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } else {
                sslCtx = null;
            }
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));

            bootstrap = new Bootstrap();
            bootstrap.group(GROUP)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                            }
                            p.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(8192),
                                    handler);
                        }
                    });
            channel = bootstrap.connect().sync().channel();
            handler.handshakeFuture().sync();

        } catch (Exception ex) {
            LOG.error(ex.toString());
        }
    }
    /**
     * 发送消息
     * @param message
     * @return
     */
    public void sendMessage(WsMessageDTO message){
        try {
            if (channel != null && channel.isActive()) {
                ChannelFuture channelFuture = channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            LOG.info("发送成功！");
                        } else {
                            LOG.error("发送失败!");
                        }
                    }
                });
            } else {
                LOG.error("发送失败,服务器连接失败");
            }
        }catch (Exception ex){
            destroy();
            LOG.error(ex.toString());
        }
    }
    /**
     *
     * 断线重连
     */
    private void doConnect()  {

        if (channel != null && channel.isActive()) {
            return;
        }
        final String wsAddress =appConfig.getRemoteAddress();
        URI uri=null;
        try {
             uri = new URI(wsAddress);
        }catch (Exception e) {
            LOG.error(e.toString());
            return;
        }
        ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort());
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    System.out.println("Connect to server successfully!");
                } else {
                    System.out.println("Failed to connect to server, try connect after 10s");

                    futureListener.channel().eventLoop().schedule(new Runnable()  {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }
    /**
     *
     * 关闭连接
     * */
    public void closeConnect() {
        if(channel != null) {
            channel.close();
        }
    }
    /**
     *
     * 强制关闭，释放资源
     **/
    public void destroy() {
        closeConnect();
        GROUP.shutdownGracefully();
    }

}
