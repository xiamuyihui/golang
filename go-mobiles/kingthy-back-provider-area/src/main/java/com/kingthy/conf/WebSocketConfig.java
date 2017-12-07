package com.kingthy.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author likejie
 * @date 2017/10/23.
 */
@Configuration
public class WebSocketConfig {

    /**计价服务脚本路径**/
    @Value("${websocketurl}")
    private  String websocketurl;

    public String getWebsocketurl() {
        return websocketurl;
    }

    public void setWebsocketurl(String websocketurl) {
        this.websocketurl = websocketurl;
    }
}
