package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xumin
 * @Description: 买家秀评论
 * @DATE Created by 9:55 on 2017/5/25.
 * @Modified by:
 */
@Data
@ToString
public class BuyersShowDTO implements Serializable{

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论人")
    private String memberName;

    private String buyersUuid;

    /**
     * 用户uuid
     */
    private String memberUuid;

    @ApiModelProperty("评论人头像")
    private String icon;

    /**
     * 评论图片 多个用 逗号 "," 分隔
     */
    @ApiModelProperty("评论图片 多个用 逗号 \",\" 分隔")
    private String images;

    @ApiModelProperty("时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;


}
