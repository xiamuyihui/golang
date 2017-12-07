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
    public static final ConcurrentHashMap<String, IEndpoint> CLIENTS =
        new ConcurrentHashMap<>();

    public static  long  connectionCount=0;

    public static IEndpoint get(String key)
    {
        if (key != null)
        {
            return CLIENTS.get(key);
        }
        return null;
    }
    
    public static void remove(String key)
    {
        if (key != null && CLIENTS.containsKey(key))
        {
            CLIENTS.remove(key);
            connectionCount--;
        }
    }
    
    public static void put(String key, IEndpoint endpoint)
    {
        if (key != null && !CLIENTS.containsKey(key))
        {
            connectionCount++;
            CLIENTS.put(key, endpoint);
        }
    }
}
