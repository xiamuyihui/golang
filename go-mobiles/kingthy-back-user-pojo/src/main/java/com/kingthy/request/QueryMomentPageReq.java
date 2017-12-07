package com.kingthy.request;

import com.kingthy.entity.Moments;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 *
 * QueryMomentPageReq(描述其作用)
 * @author 赵生辉
 * @date  2017年05月19日 14:59
 *
 */
@Data
@ToString
public class QueryMomentPageReq  extends Moments implements Serializable
{
    private Integer pageNum;

    private Integer pageSize;

    private String order;

    private List<String> members;

    @ApiModelProperty(name = "开始时间")
    private Date startTime;

    @ApiModelProperty(name = "结束时间")
    private Date endTime;

    private String token;
}
