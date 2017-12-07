package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:45 on 2017/5/24.
 * @Modified by:
 */
@Data
@ToString
public class GoodsDTO {

    @ApiModelProperty("商品中图")
    private List<String> midImgs;

    @ApiModelProperty("大图详情")
    private List<String> maxImgs;

    @ApiModelProperty("商品基本信息")
    private GoodsInfo goodsInfo;

    @JsonIgnore
    @ApiModelProperty("设计师信息")
    private DesignerInfo designerInfo;

    @JsonIgnore
    @ApiModelProperty("体型数据(人模)")
    private List<FigureInfo> figureInfoList;

    @JsonIgnore
    @ApiModelProperty("产品参数")
    private ProductInfo productInfo;

    @JsonIgnore
    @ApiModelProperty("买家秀")
    private List<BuyersShowInfo> buyersShowInfoList;
    /**
     * 商品价格 名称 快递 生产周期 月销量
     */
    @Data
    @ToString
    public static class GoodsInfo implements Serializable{
        /**
         * 参考价格
         */
        @ApiModelProperty("参考价格")
        private BigDecimal price;

        @ApiModelProperty("商品名称")
        private String goodsName;

        private String uuid;

        /**
         * 运费
         */
        @ApiModelProperty("运费")
        private BigDecimal freight;

        @ApiModelProperty("生产周期")
        private Long productionCycle;

        @ApiModelProperty("月销量")
        private Long monthSale;

        @ApiModelProperty("缩略图")
        private String shrinkageMap;

        @ApiModelProperty("是否收藏")
        private boolean favorite;

        @ApiModelProperty("买家秀总数")
        private Long commentCount;
    }

    /**
     * 设计师名称 ID 是否关注 已发布的设计数 头像
     */
    @Data
    @ToString
    public static class DesignerInfo implements Serializable{
        @ApiModelProperty("设计师名称")
        private String designerName;

        @ApiModelProperty("设计师uuid")
        private String designerUuid;

        @ApiModelProperty("是否关注")
        private boolean follow;

        @ApiModelProperty("已发布的设计数")
        private Long number;

        @ApiModelProperty("头像")
        private String icon;
    }

    /**
     * 体型数据(人模)
     */
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FigureInfo implements Serializable{
        @ApiModelProperty("人模名称")
        private String figureName;

        @ApiModelProperty("人模uuid")
        private String figureUuid;

        @ApiModelProperty("价格")
        private BigDecimal price;
    }

    /**
     *
     *   产品参数
     *
     *   流行元素 袖长 服装版型  领型  袖型  成分含量  面料 适合年龄  风格
     *
     */
    @Data
    @ToString
    public static class ProductInfo implements Serializable{

        @ApiModelProperty("流行元素")
        private String popular;

        @ApiModelProperty("袖长")
        private String sleeved;

        @ApiModelProperty("服装版型")
        private String clothing;

        @ApiModelProperty("领型")
        private String collar;

        @ApiModelProperty("袖型")
        private String sleevedStyle;

        @ApiModelProperty("成分含量")
        private String component;

        @ApiModelProperty("面料")
        private String materiel;

        @ApiModelProperty("适合年龄")
        private String fitAge;

        @ApiModelProperty("风格")
        private String style;

        @ApiModelProperty("商品属性")
        private GoodsAttribute goodsAttribute;
    }

    /**
     * 买家秀
     */
    @Data
    @ToString
    public static class BuyersShowInfo implements Serializable{

        @ApiModelProperty("评论内容")
        private String content;

        @ApiModelProperty("评论人")
        private String memberName;

        @ApiModelProperty("评论人头像")
        private String memberIcon;

        @ApiModelProperty("评论图片")
        private List<String> img;
    }

    @Data
    @ToString
    public static class CoverInfo implements Serializable{
        private String uuid;
        private String cover;
        private String materielInfo;
    }
}
