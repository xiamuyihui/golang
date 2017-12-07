package com.kingthy.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 描述：缓存更新队列配置
 * @author  likejie
 * @date 2017/10/10.
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
