/**
 * 系统项目名称
 * com.kingthy.websocket
 * SocketConnection.java
 * 
 * 2017年5月19日-下午3:10:32
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.websocket;

import javax.websocket.Session;

/**
 *
 * 连接对象
 * 
 * 李克杰 2017年5月19日 下午3:10:32
 * 
 * @version 1.0.0
 *
 */
public interface ISocketConnection
{
    
    void onClose();
    
    void onReceiveMessage(Session session, String message);
    
    void send(String message);
    
    void onError(Session session, Throwable error);
}
