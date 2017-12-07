package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:07 on 2017/7/14.
 * @Modified by:
 */
@Data
@ToString
public class MessageInfoDto implements java.io.Serializable{

    private String uuid;
    private String title;
    private String content;
    private String url;
    private String pushTime;
    /**
     * 第三短信平台
     */
    private String messagePlatformUuid;
    private String pushTarget;
    private Boolean pushStatus;
}
