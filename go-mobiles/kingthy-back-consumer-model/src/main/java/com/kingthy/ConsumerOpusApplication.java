package com.kingthy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ConsumerOpusApplication
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerOpusApplication.class);

    /*RestTemplate restTemplate;

    public ConsumerOpusApplication()
    {
        restTemplate = new RestTemplate();
    }
*/
    public static void main(String[] args)
    {
        LOGGER.info("start execute ConsumerRegisterApplication....\n");
        SpringApplication.run(ConsumerOpusApplication.class, args);
        LOGGER.info("end execute ConsumerRegisterApplication....\n");
    }
}