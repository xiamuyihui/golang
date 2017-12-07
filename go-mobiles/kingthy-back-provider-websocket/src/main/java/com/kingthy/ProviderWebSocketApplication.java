package com.kingthy;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;


/**
 * Created by likejie on 2017/6/7.
 */
@SpringCloudApplication
public class ProviderWebSocketApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ProviderWebSocketApplication.class, args);
    }
}
