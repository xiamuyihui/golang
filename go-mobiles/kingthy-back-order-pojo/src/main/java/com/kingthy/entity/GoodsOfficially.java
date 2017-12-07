package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:xumin
 * @Description:
 * @Date:16:36 2017/7/5
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsOfficially extends BaseTableFileds
{
    
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
    
    private String goodsStyleUuid;
    
    private String goodsSeasonUuid;
    
    private String goodsDetails;
    
    private String opusUuid;
    
    private String goodsTags;
    
    private String goodsImage;
    
    private String partInfo;
    
    private String materielInfo;
    
    private String accessoriesInfo;
    
    private String cover;

    private String sn;

    private String goodsUuid;

    private static final long serialVersionUID = 1L;

    /**
     * 商品类型 1 上衣 2 裤装 3 裙装 4 套装
     */
    private Integer goodsCategoryType;
    
}