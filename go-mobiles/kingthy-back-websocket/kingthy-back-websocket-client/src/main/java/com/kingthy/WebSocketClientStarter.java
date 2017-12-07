package com.kingthy;

import com.kingthy.conf.AppConfig;
import com.kingthy.wsclient.WebSocketClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 描述：注入WebSocketClient 实例
 *
 * @author likejie
 * @date 2017/11/2
 */
@Component
@EnableConfigurationProperties(value= AppConfig.class)
@ConditionalOnClass(WebSocketClient.class)
public class WebSocketClientStarter {

    @Bean
    @ConditionalOnMissingBean(WebSocketClient.class)
    public WebSocketClient getWebSocketClient(){
        return new WebSocketClient();
    }

}
