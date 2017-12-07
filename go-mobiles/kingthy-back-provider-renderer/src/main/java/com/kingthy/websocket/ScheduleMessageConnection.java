/**
 * 系统项目名称
 * com.kingthy.websocket
 * CommonMessageService.java
 * 
 * 2017年5月19日-下午2:31:47
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.kingthy.util.SpringContextUtils;

/**
 *
 * 生成进度消息发送
 * 
 * 李克杰 2017年5月19日 下午2:31:47
 * 
 * @version 1.0.0
 *
 */
@ServerEndpoint(value = "/websocket/{token}/{timespan}")
@Component
public class ScheduleMessageConnection implements ISocketConnection
{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleMessageConnection.class);
    
    private Session session;
    
    private String connectionId;
    
    public String getConnectionId()
    {
        return connectionId;
    }
    
    public void setConnectionId(String connectionId)
    {
        this.connectionId = connectionId;
    }
    
    public Session getSession()
    {
        return session;
    }
    
    public void setSession(Session session)
    {
        this.session = session;
        
    }
    
    StringRedisTemplate stringRedisTemplate;
    
    public ScheduleMessageConnection()
    {
        // 初始化缓存对象
        stringRedisTemplate = (StringRedisTemplate)SpringContextUtils.getBeanByClass(StringRedisTemplate.class);
    }
    
    /**
     * 从token中获取登录用户
     * 
     */
    protected String getLoginerByToken(String token)
    {
        String memberUuid = null;
        if (StringUtils.isNotBlank(token))
        {
            memberUuid = stringRedisTemplate.opsForValue().get(token);
        }
        return memberUuid;
    }
    
    /**
     * 
     * 开始连接
     * 
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token, @PathParam("timespan") String timespan)
    {
        try
        {
            setSession(session);
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isBlank(memberUuid) || StringUtils.isBlank(timespan))
            {
                // 参数无效
                send("参数无效，请求连接失败，即将关闭！");
                closeConnection();
                return;
            }
            this.connectionId = memberUuid + timespan;
            SocketClient.put(this.connectionId, this);
        }
        catch (Exception e)
        {
            logger.error("连接失败", e);
        }
        
    }
    
    private void closeConnection()
    {
        try
        {
            if (this.session.isOpen())
            {
                this.session.close();
            }
        }
        catch (IOException e)
        {
            logger.error("连接关闭失败", e);
        }
    }
    
    /**
     * 连接关闭调用的方法
     * 
     */
    @OnClose
    @Override
    public void onClose()
    {
        SocketClient.remove(this.connectionId);
    }
    
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    @Override
    public void onReceiveMessage(Session session, String message)
    {
        // TODO Auto-generated method stub
        send("我已收到消息：" + message);
    }
    
    /**
     * 发送消息
     * 
     */
    @Override
    public void send(String message)
    {
        
        try
        {
            if (this.session.isOpen())
            {
                this.session.getBasicRemote().sendText(message);
            }
        }
        catch (IOException e)
        {
            logger.error("ScheduleMessageConnection.send", e);
        }
    }
    
    @OnError
    @Override
    public void onError(Session session, Throwable error)
    {
        // TODO Auto-generated method stub
        logger.error("ScheduleMessageConnection.onError", error);
    }
    
}
