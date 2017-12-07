package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * GoodsDto(单个商品详细信息出参封装类)---运营平台服务接口使用
 * 
 * @author 陈钊 2017年4月24日 上午9:41:10
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
public class GoodsInfo implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    private String goodsName;
    
    private String goodsFeature;
    
    private BigDecimal standardPrice;
    
    private String opusSn;
    
    private String desinger;
    
    private Integer returnPoint;
    
    private Integer putOnMethod;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date putOnTime;
    
    private Integer status;
    
    private String goodsCategoryUuid;
    
    private String className;
    
    private String goodsStyleUuid;
    
    private String styleName;
    
    private String goodsSeasonUuid;
    
    private String seasonName;
    
    private String goodsDetails;
    
    private String opusUuid;
    
    private String goodsTags;
    
    private String goodsImage;
    
    private String partInfo;
    
    private String materielInfo;
    
    private String accessoriesInfo;
    
    private String cover;
    
    private String uuid;
}
