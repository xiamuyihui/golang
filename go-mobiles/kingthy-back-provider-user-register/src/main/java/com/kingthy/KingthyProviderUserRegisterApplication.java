package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
/**
 *
 * 启动类
 * @author pany
 *
 */
public class KingthyProviderUserRegisterApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderUserRegisterApplication.class, args);
    }
}
