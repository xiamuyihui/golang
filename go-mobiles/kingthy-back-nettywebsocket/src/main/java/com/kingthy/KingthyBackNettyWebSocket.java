package com.kingthy;


import com.kingthy.server.NettyServer;

/**
 * 启动程序
 * Created by likejie on 2017/10/23.
 */
public class KingthyBackNettyWebSocket {

    public static void main(String[] args) throws Exception {
        //服务启动
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
