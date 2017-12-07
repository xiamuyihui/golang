package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * MassageApplication
 * <p>
 * @author yuanml 2017/8/21
 *
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MessageApplication.class, args);
    }
}
