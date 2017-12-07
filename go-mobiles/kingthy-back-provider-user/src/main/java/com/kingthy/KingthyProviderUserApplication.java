package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 启动类
 * @author likejie
 */
@SpringCloudApplication
@EnableFeignClients
public class KingthyProviderUserApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderUserApplication.class, args);
    }
}
