package com.kingthy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * WebSocketConfig
 * 
 * 李克杰 2017年5月19日 下午3:39:32
 * 
 * @version 1.0.0
 *
 */
@Configuration
public class WebSocketConfig
{
    
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        
        return new ServerEndpointExporter();
    }
}
