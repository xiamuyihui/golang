package com.kingthy.websocket;


import org.springframework.stereotype.Component;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by likejie on 2017/6/9.
 */
@ServerEndpoint(value = "/websocket-mobilelogin")
@Component
public class MobileLoginEndpoint extends BaseEndpoint {
    /**
     *
     * 开始连接
     *
     */
    @OnOpen
    public void onOpen(Session session)
    {
        try
        {
            initConnection(session);
        }
        catch (Exception e)
        {
            logger.error("连接失败", e);
        }

    }
}
