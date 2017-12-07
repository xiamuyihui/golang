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


import com.kingthy.dto.WSMessageDTO;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;



/**
 *
 * PC端登录，并生成二维码
 * 
 * 李克杰 2017年5月19日 下午2:31:47
 * 
 * @version 1.0.0
 *
 */
@ServerEndpoint(value = "/websocket-pclogin")
@Component
public class PcLoginEndPoint extends BaseEndpoint{


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
            //发送唯一标识，用于二维码生成
            WSMessageDTO dto=new WSMessageDTO();
            //dto.setMessage(connectionId);
            //dto.setMsType(WebSocketMessageType.QRCODE.getValue());
            send(dto);
        }
        catch (Exception e)
        {
            logger.error("onOpen error", e);
            logger.error("连接失败", e);
        }
        
    }
}
