package com.kingthy.websocket;

import com.alibaba.fastjson.JSON;
import com.kingthy.dto.WSMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.util.UUID;


/**
 *
 * Created by likejie on 2017/6/8.
 */
public abstract class BaseEndpoint implements IEndpoint {

    protected static final Logger logger = LoggerFactory.getLogger(BaseEndpoint.class);

    /**
     *
     * 当前连接的会话
     */
    protected Session session;
    /**
     *
     * 当前连接的id
     */
    protected String connectionId;


    @Override
    public Session getSession() {
        return session;
    }
    @Override
    public String getConnectionId() {
        return connectionId;
    }
    /**
     *
     * 创建连接对象
     *
     */
    protected void initConnection(Session session) {

        try {
            this.session=session;
            this.connectionId= UUID.randomUUID().toString();
            SocketClient.put(connectionId, this);
        } catch (Exception e) {
            logger.error("initConnection error", e);
        }
    }
    public void closeConnection()
    {
        try
        {
            if (this.session.isOpen())
            {
                this.session.close();
            }
        }
        catch (Exception e)
        {
            logger.error("closeConnection error", e);
        }
    }

    /**
     * 连接关闭调用的方法
     *
     */
    @OnClose
    public void onClose()
    {
        try{
            SocketClient.remove(this.connectionId);
        }catch (Exception e){
            logger.error("onClose error", e);
        }

    }
    @OnMessage
    public void onMessage(Session session, String message) {
        onReceiveMessage(session,message);
    }
    @Override
    public void onReceiveMessage(Session session, String message) {

    }
    /**
     * 发送消息
     *
     */
    @Override
    public void send(WSMessageDTO dto)
    {
        try
        {
            if (this.session.isOpen())
            {
                String message=JSON.toJSONString(dto);
                this.session.getBasicRemote().sendText(message);
            }
        }
        catch (Exception e)
        {
            logger.error("send error", e);
        }
    }

    @Override
    public void onError(Session session, Throwable error) {

        closeConnection();
        logger.error("onError error", error);
    }
}
