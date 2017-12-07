package com.kingthy.server;


import com.alibaba.fastjson.JSON;
import com.kingthy.dto.WsMessageDTO;
import com.kingthy.dto.WsRequestCode;
import com.kingthy.dto.WsResponseCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 描述：扩展SimpleChannelInboundHandler，用于处理TextWebSocketFrame 信息
 * SimpleChannelInboundHandler 发送完消息会自动释放
 * @author  likejie
 * @date 2017/10/16.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final  Logger LOG = LoggerFactory.getLogger(HttpRequestHandler.class);
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }
    /**
     *
     * 处理自定义事件
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            LOG.info("握手成功！建立websocket连接..........");
            //握手成功，从ChannelPipeline 中删除HttpRequestHandler，不在接受http消息
            ctx.pipeline().remove(HttpRequestHandler.class);
            //加入当前, 上线人员推送前端，显示用户列表中去
            Channel channel = ctx.channel();
            group.add(channel);
            /**保存客户端连接对象***/
            NettyConstants.CHANNEL_CLIENTS.put(channel.id().asShortText(),channel);
            /**握手成功后，才能发送websocket消息**/

            /**应答*/
            replyMessage(ctx, WsResponseCode.SUCCESS,"连接成功！ 当前连接数："+group.size());
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        LOG.info("接收到客户端消息："+msg.text());
        //消息处理
        handleMessage(ctx,msg);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("客户端已下线");
        offlines(ctx);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LOG.info("Current channel handlerRemoved");
        offlines(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("异常："+ cause.getMessage());
        offlines(ctx);
    }
    /**
     *
     * 下线
     */
    private void offlines(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        group.remove(channel);
        NettyConstants.CHANNEL_CLIENTS.remove(channel.id().asShortText());
        ctx.close();
    }
    /**
     * 消息处理
     */
    private void handleMessage(ChannelHandlerContext ctx,TextWebSocketFrame frame){

        try {
             /**登录token**/
             String token = ctx.channel().attr(NettyConstants.CHANNEL_TOKEN_KEY).get();
             WsMessageDTO message = JSON.parseObject(frame.text(), WsMessageDTO.class);
             //消息请求码
             Integer requestCode=message.getRequestCode();
             if(requestCode==null){
                 replyMessage(ctx, WsResponseCode.CONNECTID_ERROR, "requestCode 无效！");
                 return;
             }
             //转发
             if(requestCode== WsRequestCode.FORWORD.getValue()){
                 //接收的客户端
                 Channel clientChannel=NettyConstants.CHANNEL_CLIENTS.get(message.getReceiveChannelId());
                 if(clientChannel==null){
                     replyMessage(ctx, WsResponseCode.CONNECTID_ERROR, "receiveChannelId 无效，无法发送到指定客户端！");
                     return;
                 }
                 message.setResponseCode(WsResponseCode.INFO.getValue());
                 sendMessage(clientChannel,message);
             //二维码登录，通知PC端登录
             }else if(requestCode== WsRequestCode.QRCODE_LOGIN_AUTH.getValue()){
                 //接收的客户端
                 Channel clientChannel=NettyConstants.CHANNEL_CLIENTS.get(message.getReceiveChannelId());
                 if(clientChannel==null){
                     replyMessage(ctx, WsResponseCode.CONNECTID_ERROR, "receiveChannelId 无效，无法发送到指定客户端！");
                     return;
                 }
                 if(StringUtils.isBlank(message.getToken())){
                     replyMessage(ctx, WsResponseCode.TOKEN_ERROR, "token 无效");
                     return;
                 }
                 message.setResponseCode(WsResponseCode.QRCODE_LOGIN_SUCCESS.getValue());
                 message.setContent("二维码登录成功");
                 sendMessage(clientChannel,message);
             }else{
                 //TODO..........
             }

        } catch (Exception ex) {
            LOG.error("消息格式不正确，解析失败！");
            replyMessage(ctx, WsResponseCode.PARSE_ERROR, "消息格式不正确，解析失败！");
        }
    }
    /**
     *  应答
     */
    private void replyMessage(ChannelHandlerContext ctx, WsResponseCode type, String msg){
        WsMessageDTO dto=new WsMessageDTO();
        dto.setContent(msg);
        dto.setChannelId(ctx.channel().id().asShortText());
        dto.setResponseCode(type.getValue());
        TextWebSocketFrame data=new TextWebSocketFrame(JSON.toJSONString(dto));
        ctx.writeAndFlush(data);
    }
    /**
     * 发送消息到指定的客户端
     */
    private void sendMessage(Channel channel,WsMessageDTO dto){
        if(channel!=null&&dto!=null) {
            TextWebSocketFrame data = new TextWebSocketFrame(JSON.toJSONString(dto));
            channel.writeAndFlush(data);
        }
    }
}
