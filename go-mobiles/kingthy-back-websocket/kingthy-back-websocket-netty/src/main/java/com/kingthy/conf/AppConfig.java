package com.kingthy.conf;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 描述：服务配置
 * @author  likejie
 * @date 2017/11/1.
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "websocket")
public  class AppConfig {

    /**
     *
     * netty 相关配置
     */
    private NettyConfig netty;

    /**
     *
     * zookeeper 相关配置
     */
    private ZkConfig zk;

    /**
     *
     * redis 相关配置
     */
    private String redis;

}