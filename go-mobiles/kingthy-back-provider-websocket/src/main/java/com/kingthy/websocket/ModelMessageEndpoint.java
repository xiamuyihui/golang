package com.kingthy.websocket;

import com.kingthy.cache.RedisManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


/**
 * ModelMessage(用于生成人体模型后的消息推送)
 * <p>
 * 赵生辉 2017年06月19日 15:07
 *
 * @version 1.0.0
 */
@ServerEndpoint(value = "/websocket/modelMessage/{token}")
@Component
public class ModelMessageEndpoint extends BaseEndpoint
{

    @Autowired
    private RedisManager redisManager;

    @OnOpen
    public void onOpen(Session session ,@PathParam("token") String token)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if(StringUtils.isNotBlank(memberUuid)){
                //token无效
            }
            initConnection(session);
            redisManager.set("websocket"+memberUuid,connectionId);
        }
        catch (Exception e)
        {
            logger.error("连接失败", e);
        }

    }
}
