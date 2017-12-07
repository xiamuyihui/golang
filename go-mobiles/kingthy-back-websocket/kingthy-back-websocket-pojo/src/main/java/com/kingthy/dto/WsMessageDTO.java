package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 消息包
 * @author  likejie  2017/11/1.
 */
@Data
@ToString
public class WsMessageDTO implements Serializable {

    /**
     *
     * 用户token
     */
    private String token;

    /**
     *  建立连接的channelId
     */
    private String channelId;

    /**
     *  接收数据的channelId
     */
    private String receiveChannelId;
    /**
     *
     *  请求码
     */
    private Integer requestCode;
    /**
     *
     * 应答码
     */
    private Integer responseCode;

    /**
     *
     * 消息内容
     */
    private String content;

}
