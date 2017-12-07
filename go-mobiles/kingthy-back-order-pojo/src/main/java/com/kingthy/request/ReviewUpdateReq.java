package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * ReviewUpdateReq
 * <p>
 * @author yuanml
 * 2017/5/16
 *
 * @version 1.0.0
 */
@Data
@ToString
public class ReviewUpdateReq implements Serializable
{
    @ApiModelProperty(value = "操作数据UUID数组")
    private String reviewUuids;
    
    @ApiModelProperty(value = "显示隐藏，true显示")
    private Boolean status;
    
    @ApiModelProperty(value = "删除标识，true删除")
    private Boolean delFlag;
}
