package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * IncrementQueryReq
 *
 * @author yuanml
 * @version 1.0.0
 */
@ToString
@Data
public class IncrementQueryReq
{
    @ApiModelProperty(value = "页码")
    private int pageNum;
    
    @ApiModelProperty(value = "每页显示数", required = true)
    private int pageSize;
    
    @ApiModelProperty(value = "当前请求最大时间", required = true)
    private Long referenceTime;
    
}
