package com.kingthy;

import com.kingthy.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 描述：启动程序
 * @author  likejie
 * @date 2017/10/23.
 */
@SpringBootApplication
public class KingthyBackWebSocketApplication implements CommandLineRunner {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(KingthyBackWebSocketApplication.class, args);
    }

    @Autowired
    private NettyServer nettyServer;

    @Bean
    public NettyServer getNettyServer(){
        return new NettyServer();
    }

    @Override
    public void run(String... strings) throws Exception {
        //启动服务
        ChannelFuture channelFuture= nettyServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                nettyServer.destroy();
            }
        });
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
