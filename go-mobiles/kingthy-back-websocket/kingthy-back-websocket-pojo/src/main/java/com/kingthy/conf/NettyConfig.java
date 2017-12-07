package com.kingthy.conf;

import lombok.Data;
import lombok.ToString;

/**
 * netty 配置
 * @author likejie
 * @date 2017/11/1
 */
@Data
@ToString
public class NettyConfig {

    private String host;

    private Integer port;

    private Boolean isCluster;

    private Integer maxHeartbeat;
}
