package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
/**
 * @author:xumin
 * @Description:
 * @Date:17:25 2017/11/1
 */
public class KingthyProviderIncomeApplication
{
    
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderIncomeApplication.class, args);
    }
}


