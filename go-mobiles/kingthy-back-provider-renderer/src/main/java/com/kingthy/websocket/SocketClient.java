/**
 * 系统项目名称
 * com.kingthy.websocket
 * ConnectionFactory.java
 * 
 * 2017年5月19日-下午3:29:41
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.websocket;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 连接的客户端
 * 
 * 李克杰 2017年5月19日 下午3:29:41
 * 
 * @version 1.0.0
 *
 */
public class SocketClient
{
    // 客户端连接
    public static ConcurrentHashMap<String, ISocketConnection> CLIENTS =
        new ConcurrentHashMap<String, ISocketConnection>();
    
    public static ISocketConnection get(String connectionId)
    {
        if (connectionId != null)
        {
            return CLIENTS.get(connectionId);
        }
        return null;
    }
    
    public static void remove(String connectionId)
    {
        if (connectionId != null && CLIENTS.containsKey(connectionId))
        {
            CLIENTS.remove(connectionId);
        }
    }
    
    public static void put(String connectionId, ISocketConnection socketConnection)
    {
        if (connectionId != null && !CLIENTS.containsKey(connectionId))
        {
            CLIENTS.put(connectionId, socketConnection);
        }
    }
}
