package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author shenghuizhao
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class KingthyProviderOpuApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderOpuApplication.class, args);
    }
}
