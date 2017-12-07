package com.kingthy.server;


import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

/**
 *  定义常量
 * Created by likejie on 2017/10/16.
 */
public class NettyConstants {

    public static final AttributeKey<String> CHANNEL_TOKEN_KEY = AttributeKey.valueOf("netty.channel.token");

    // 客户端连接
    public static final ConcurrentHashMap<String, Channel> CHANNEL_CLIENTS =
            new ConcurrentHashMap<>();

    /**注册到zookeeper的路径**/
    public static final String ZK_CLUSTER_PATH="/netty-cluster";
}
