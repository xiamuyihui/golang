package com.kingthy.conf;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：----------
 *
 * @author likejie
 * @date 2017/11/2
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "websocket")
public class AppConfig {
    /**
     *
     * 服务端连接地址
     */
    private String remoteAddress;

}
