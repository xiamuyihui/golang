package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Created by likejie on 2017/6/9.
 */
@Data
@ToString
public class BuyersShowListDTO implements Serializable
{

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论人")
    private String memberName;

    @ApiModelProperty("买家秀UUID")
    private String buyersUuid;

    @ApiModelProperty("评论人头像")
    private String icon;

    @ApiModelProperty("评论图片")
    private List<String> images;

    @ApiModelProperty("时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("点赞数量")
    private Long likeCount;

    @ApiModelProperty("是否点赞")
    private Boolean isLiked;

    @Data
    @ToString
    public static class LikesDTO
    {
        private String momentUuid;

        private Long likeCount;

    }
}
