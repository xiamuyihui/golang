package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 描述：启动类
 * @author  likejie
 * @date 2017/10/10.
 */
@SpringCloudApplication
@EnableFeignClients
public class KingthyProviderClothingApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderClothingApplication.class, args);
    }
}
