package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:15 on 2017/7/14.
 * @Modified by:
 */
@Data
@ToString
public class MessageInfoReq implements Serializable {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页展示的数量
     */
    private Integer pageSize;

    @ApiModelProperty("编号")
    private String uuid;

    @ApiModelProperty("发布对象")
    private String pushTarget;

    @ApiModelProperty("发布人")
    private String memberName;

    @ApiModelProperty("发布内容")
    private String content;

    @ApiModelProperty("发布时间")
    private Long beginTime;

    @ApiModelProperty("发布时间")
    private Long endTime;

    @Data
    @ToString
    public static class CreateMessageDto implements Serializable{

        @ApiModelProperty("title")
        private String title;

        @ApiModelProperty("消息内容")
        private String content;

        @ApiModelProperty("链接")
        private String url;

        @ApiModelProperty("发布时间")
        private Date pushTime;

        @ApiModelProperty("发送短信平台")
        private String messagePlatformUuid;

        @ApiModelProperty("用户")
        private String membersUuid;

        @ApiModelProperty("发布对象")
        private String pushTarget;

        @ApiModelProperty("发布状态")
        private Boolean pushStatus;

    }

    @Data
    @ToString
    public static class EditMessageDto extends CreateMessageDto{

        private String uuid;
    }
}
