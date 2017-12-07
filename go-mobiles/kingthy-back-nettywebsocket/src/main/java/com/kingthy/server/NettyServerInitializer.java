package com.kingthy.server;

import com.kingthy.conf.NConfg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 初始化netty
 * Created by likejie on 2017/10/16.
 */
public class NettyServerInitializer extends ChannelInitializer<Channel> {

    private static final String WEBSOCKET_PATH = "/websocket";
    private static final boolean SSL = System.getProperty("ssl") != null;
    private final ChannelGroup group;

    public NettyServerInitializer(ChannelGroup group) {
        this.group = group;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        final SslContext sslCtx;
        //消息安全
        if (SSL) {

            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            pipeline.addLast(sslCtx.newHandler(channel.alloc()));
        }
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //处理心跳，每隔一定时间检测客户端
        pipeline.addLast(new IdleStateHandler(0, 0, NConfg.server.max_heartbeat, TimeUnit.SECONDS));
        pipeline.addLast(new NettyHeartbeatHandler());
        //讲请求和应答解码为http消息
        pipeline.addLast(new HttpServerCodec());
        //向客户端发送html5文件
        pipeline.addLast(new ChunkedWriteHandler());
        //将http消息多个部分合成一条完整的http消息
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //http请求
        pipeline.addLast(new HttpRequestHandler(WEBSOCKET_PATH));
        //其他websocket请求
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH));
        pipeline.addLast(new TextWebSocketFrameHandler(group));


    }
}
