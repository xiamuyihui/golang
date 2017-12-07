package com.kingthy.controller;

import com.kingthy.wsclient.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：----------
 *
 * @author likejie
 * @date 2017/11/2
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private  WebSocketClient webSocketClient;

    @GetMapping("/websocket")
    public void test(){
        webSocketClient.connect();
    }
}
