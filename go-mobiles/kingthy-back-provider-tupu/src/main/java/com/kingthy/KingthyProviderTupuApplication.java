package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KingthyProviderTupuApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderTupuApplication.class, args);
    }
}
