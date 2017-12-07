package com.kingthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
/**
 * @author:xumin
 * @Description:
 * @Date:16:27 2017/11/1
 */
public class KingthyProviderJobApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderJobApplication.class, args);
    }

}


