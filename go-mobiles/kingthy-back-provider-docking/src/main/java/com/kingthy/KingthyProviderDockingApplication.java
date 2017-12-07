package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableCaching
/**
 * @author:xumin
 * @Description:
 * @Date:14:31 2017/11/1
 */
public class KingthyProviderDockingApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderDockingApplication.class, args);
    }

}


