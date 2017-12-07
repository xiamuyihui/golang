package com.kingthy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;


/**
 * 描述：启动程序
 * @author  likejie
 * @date 2017/10/23.
 */
@SpringCloudApplication
public class FileUploadApplication
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadApplication.class);
    
    public static void main(String[] args)
    {
        LOGGER.info("start execute ConsumerRegisterApplication....\n");
        SpringApplication.run(FileUploadApplication.class, args);
        LOGGER.info("end execute ConsumerRegisterApplication....\n");
    }
}