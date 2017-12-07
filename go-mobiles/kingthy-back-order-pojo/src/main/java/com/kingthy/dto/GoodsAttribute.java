package com.kingthy.dto;

import com.kingthy.entity.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * GoodsAttribute(创建作品的参数实体)
 * <p>
 * @author 赵生辉 2017年08月24日 10:54
 *
 * @version 1.0.0
 */
@Data
@ToString
public class GoodsAttribute
{

    @ApiModelProperty("上衣参数")
    private CoatAttribute coatAttribute;

    @ApiModelProperty("裤装参数")
    private PantsAttribute pantsAttribute;

    @ApiModelProperty("连衣裙参数")
    private DressAttribute dressAttribute;

    @ApiModelProperty("套装参数")
    private SuitAttribute suitAttribute;

    @ApiModelProperty("裙装参数")
    private SkirtAttribute skirtAttribute;

    /**
     * 上衣参数
     */
    @Data
    @ToString
    public class CoatAttribute
    {
        /**
         * 服装版型
         */
        @ApiModelProperty(value ="上衣版型")
        private String clothing;

        /**
         * 袖长
         */
        @ApiModelProperty(value ="上衣袖长")
        private String sleeved;

        /**
         * 袖型
         */
        @ApiModelProperty(value ="上衣袖长")
        private String sleevedStyle;

        /**
         * 领型
         */
        @ApiModelProperty(value ="上衣领型")
        private String collar;

        /**
         * 门襟
         */
        @ApiModelProperty(value ="上衣门襟")
        private String placket;

    }

    /**
     * 裤装参数
     */
    @Data
    @ToString
    public class PantsAttribute
    {
        /**
         * 裤装版型
         */
        @ApiModelProperty(value ="裤装版型")
        private String pantsStyle;

        /**
         * 裤长
         */
        @ApiModelProperty(value ="裤长")
        private String pantsLong;

        /**
         * 腰高型
         */
        @ApiModelProperty(value ="裤子腰高型")
        private String waistType;

        /**
         * 款式细节
         */
        @ApiModelProperty(value ="裤子款式细节")
        private String styleInfo;

    }

    /**
     * 连衣裙参数
     */
    @Data
    @ToString
    public class DressAttribute
    {
        /**
         * 轮廓
         */
        @ApiModelProperty(value ="连衣裙轮廓")
        private String dressContour;

        /**
         * 连衣裙长
         */
        @ApiModelProperty(value ="连衣裙长")
        private String dressLong;

        /**
         * 连衣裙款式
         */
        @ApiModelProperty(value ="连衣裙款式")
        private String dressStyle;

        /**
         * 连衣裙腰型
         */
        @ApiModelProperty(value ="连衣裙腰型")
        private String dressWaist;

        /**
         * 连衣裙门襟
         */
        @ApiModelProperty(value ="连衣裙门襟")
        private String dressPlacket;

        /**
         * 连衣裙裙型
         */
        @ApiModelProperty(value ="连衣裙裙型")
        private String dressType;

        /**
         * 连衣裙袖长
         */
        @ApiModelProperty(value ="连衣裙袖长")
        private String dressSleeveLong;

        /**
         * 连衣裙袖型
         */
        @ApiModelProperty(value ="连衣裙袖型")
        private String dressSleeveStyle;

        /**
         * 连衣裙领型
         */
        @ApiModelProperty(value ="连衣裙领型")
        private String dressCollar;

    }

    @Data
    @ToString
    public class SuitAttribute
    {
        /**
         * 套装类型
         */
        @ApiModelProperty(value ="套装类型")
        private String suitType;

        /**
         * 套装用途
         */
        @ApiModelProperty(value ="套装用途")
        private String suitUse;

        /**
         * 套装数量
         */
        @ApiModelProperty(value ="套装数量")
        private String suitNum;

    }

    @Data
    @ToString
    public class SkirtAttribute
    {
        /**
         * 轮廓
         */
        @ApiModelProperty(value ="短裙轮廓")
        private String skirtContour;

        /**
         * 裙长
         */
        @ApiModelProperty(value ="短裙裙长")
        private String skirtLong;

        /**
         * 裙子款式
         */
        @ApiModelProperty(value ="短裙裙子款式")
        private String skirtStyle;

        /**
         * 裙子腰型
         */
        @ApiModelProperty(value ="短裙裙子腰型")
        private String skirtWaist;

    }


}


