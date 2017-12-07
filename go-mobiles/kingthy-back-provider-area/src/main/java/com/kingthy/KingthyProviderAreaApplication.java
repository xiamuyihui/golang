package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author shenghuizhao
 */
@EnableDiscoveryClient
@SpringBootApplication
public class KingthyProviderAreaApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderAreaApplication.class, args);
    }
}
