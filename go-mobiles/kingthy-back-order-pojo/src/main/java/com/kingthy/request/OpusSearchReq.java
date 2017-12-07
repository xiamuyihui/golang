/**
 * 系统项目名称
 * com.kingthy.platform.dto.opus
 * OpusReq.java
 * 
 * 2017年4月12日-下午8:09:37
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * OpusSearchReq(作品搜索时传入参数)
 * 
 * @author yuanml 2017年4月12日 下午8:09:37
 * 
 * @version 1.0.0
 *
 */
@Data
public class OpusSearchReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索设计师")
    private String memberNick;

    @ApiModelProperty(value = "搜索时间前空格")
    @JsonFormat(timezone = "GMT+8")
    private Date beginDate;

    @ApiModelProperty(value = "搜索时间后空格")
    @JsonFormat(timezone = "GMT+8")
    private Date endDate;

    private Integer pageNum;
    
    private Integer pageSize;

    @ApiModelProperty("作品名称")
    private String opusName;

    @ApiModelProperty("作品状态")
    private Integer opusStatus;

    @ApiModelProperty("是否显示")
    private Boolean isShow;

    private String remark;

    private String memberUuid;
}
