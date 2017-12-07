package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 * GoodsParam(商品分页查询输入参数)
 * 
 * @author 陈钊 2017年4月6日 上午9:08:02
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class GoodsPageReq implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private int pageSize;
    
    private int pageNum;

    private String goodsCategoryUuid;
    @JsonIgnore
    private String goodsStyleUuid;
    @JsonIgnore
    private String goodsSeasonUuid;
    @JsonIgnore
    private List<String> userList;

    private Integer status;
    
    private String goodsName;
    
    private String desinger;

    private Long beginTime;
    private Long endTime;
    private Double priceMin;
    private Double priceMax;

    @ApiModelProperty("商品类型")
    private String goodsCategoryType;
}