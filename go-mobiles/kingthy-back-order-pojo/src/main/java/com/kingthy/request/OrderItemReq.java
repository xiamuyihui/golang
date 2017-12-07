package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:58 on 2017/7/17.
 * @Modified by:
 */

@Data
@ToString
public class OrderItemReq implements java.io.Serializable {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页展示的数量
     */
    private Integer pageSize;

    @ApiModelProperty("订单号")
    private String sn;

    @ApiModelProperty("会员")
    private String memberName;

    @ApiModelProperty("设计师")
    private String desingerName;

    @ApiModelProperty("订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成")
    private Integer status;

    @ApiModelProperty("下单时间")
    private Long beginTime;

    @ApiModelProperty("下单时间")
    private Long endTime;

    private Long priceMin;

    private Long priceMax;

    @JsonIgnore
    private List<String> productUuids;
}
