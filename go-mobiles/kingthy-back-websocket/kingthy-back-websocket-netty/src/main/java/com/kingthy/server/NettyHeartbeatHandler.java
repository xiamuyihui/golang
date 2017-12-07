package com.kingthy.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 描述：心跳检测
 * @author  likejie
 * @date 2017/10/16.
 */
public class NettyHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HB", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            logger.info("====>Heartbeat: greater than 180 seconds");
            /**发送心跳包到客户端，无应答，则关闭连接*/
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }else if(evt instanceof ChannelInputShutdownReadComplete){
            //远程主机强制关闭了连接
            ctx.close();
            logger.info("远程主机强制关闭了连接");
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
