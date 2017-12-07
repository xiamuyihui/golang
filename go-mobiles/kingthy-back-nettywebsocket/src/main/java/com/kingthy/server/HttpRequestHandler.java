package com.kingthy.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *  http连接，握手
 * Created by likejie on 2017/10/16.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger LOG = LoggerFactory.getLogger(HttpRequestHandler.class);
    private final String webUri;

    public HttpRequestHandler(String webUri) {
        this.webUri = webUri;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        LOG.info("http握手请求.....", request.uri());
        String uri = StringUtils.substringBefore(request.uri(), "?");

        if(webUri.equalsIgnoreCase(uri)) {//只接收以/websocket结束的请求

            //获取参数
            QueryStringDecoder query = new QueryStringDecoder(request.uri());
            Map<String, List<String>> map = query.parameters();
            List<String> tokens = map.get("token");
            //根据参数保存当前登录对象, 并把该token加入到channel中
            if(tokens != null && !tokens.isEmpty()) {
                String token = tokens.get(0);
                ctx.channel().attr(NettyConstants.CHANNEL_TOKEN_KEY).getAndSet(token);
            }
            request.setUri(uri);
            //递增引用计数器，并传递给下一个ChannelPipeline
            ctx.fireChannelRead(request.retain());

        }else {
           //TODO...............
            ctx.fireChannelRead(request.retain());
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

