package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by likejie on 2017/5/26.
 */
@SpringCloudApplication
@EnableFeignClients
public class ProviderBaseApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(ProviderBaseApplication.class, args);
    }
}
