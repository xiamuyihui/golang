package com.kingthy.conf;

import lombok.Data;
import lombok.ToString;

/**
 * 描述：redis 配置
 *
 * @author likejie
 * @date 2017/11/2
 */
@Data
@ToString
public class RedisConfig {

    private String host;
    private Integer port;
    private String password;
}
