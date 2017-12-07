package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * @author:xumin
 * @Description:
 * @Date:11:39 2017/11/2
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class KingthyProviderOrderApplication
{
    
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderOrderApplication.class, args);
    }
}


