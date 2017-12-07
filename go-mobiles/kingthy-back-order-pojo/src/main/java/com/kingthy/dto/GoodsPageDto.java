package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 *
 * GoodsPageDto(分页查询商品出参封装类)
 * 
 * @author 陈钊 2017年4月24日 上午10:00:26
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
public class GoodsPageDto implements Serializable
{
    private String uuid;
    
    private String className;
    
    private String goodsName;
    
    private String goodsCategoryUuid;
    
    private String standardPrice;
    
    private Integer status;
    
    private String desinger;
    
    private String cover;

    private String createDate;

    private String sn;

    private String goodsCategoryType;

    @Data
    @ToString
    public static class GoodsDetailDto implements java.io.Serializable{

        private String goodsName;
        private String goodsImage;
        private Double standardPrice;

        private String designer;
        @JsonIgnore
        private String goodsCategoryUuid;

        private String className;
        private String attribute;
        private String partInfo;
        private String materielInfo;
        private String accessoriesInfo;
        private String goodsDetailImage;
        private String goodsCategoryType;
    }
}
