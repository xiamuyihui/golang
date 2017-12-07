package com.kingthy.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 描述：FileUploadConfig
 * @author  none
 * @date 2017/10/23.
 */
@Configuration
public class FileUploadConfig {


    @Value("${file.serverIp}")
    private String fileServerIp;

    public String getFileServerIp() {
        return fileServerIp;
    }
}
