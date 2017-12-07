package com.kingthy.conf;

import lombok.Data;
import lombok.ToString;


/**
 * zookeeper 配置
 * @author  likejie 2017/11/1.
 */
@Data
@ToString
public  class ZkConfig {

    private String watchPath;

    private String serverAddress;

    private Restry  restry;

    @Data
    @ToString
    public static class Restry{

        private  int maxRetries;
        private  int baseSleepTimeMs;
        private  int maxSleepMs;
    }
}