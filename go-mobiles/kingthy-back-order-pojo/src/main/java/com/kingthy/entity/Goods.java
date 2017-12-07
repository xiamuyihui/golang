package com.kingthy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

/**
 * @author:xumin
 * @Description:
 * @Date:17:46 2017/11/2
 */
@Data
@ToString
public class Goods implements Serializable
{
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;

    private String goodsName;

    private String goodsFeature;

    private BigDecimal standardPrice;

    private String opusSn;

    private String desinger;

    private Integer returnPoint;

    private Integer status;

    private String goodsCategoryUuid;

    private String goodsStyleUuid;

    private Integer version;

    private String creator;

    private String modifier;

    private String opusUuid;

    private Boolean delFlag;

    private String cover;

    private Long clicks;

    private String attribute;

    private String sn;

    private String ageSegmentUuid;

    private String goodsCategoryType;

    private String goodsCategoryName;

    private String fileUrl;

    private String goodsImage;

    private String partInfo;

    private String materielInfo;

    private String accessoriesInfo;

    private String goodsDetails;

    private Boolean isPrivate;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Boolean officiallyFlag;

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    public enum Type
    {

        /**
         * 普通商品
         */
        general,

        /**
         * 兑换商品
         */
        exchange,

        /**
         * 赠品
         */
        gift
    }

    /**
     * 排序类型
     */
    public enum OrderType
    {

        /**
         * 置顶降序
         */
        topDesc,

        /**
         * 价格升序
         */
        priceAsc,

        /**
         * 价格降序
         */
        priceDesc,

        /**
         * 销量降序
         */
        salesDesc,

        /**
         * 评分降序
         */
        scoreDesc,

        /**
         * 日期降序
         */
        dateDesc
    }

    public enum GoodsStatus
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:48 2017/11/2
         */
        goodsUp(1, "已上架"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:48 2017/11/2
         */
        goodsDown(0, "已下架"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:48 2017/11/2
         */
        timingUp(2, "定时上架");

        private int value;

        private String nameValue;

        private GoodsStatus(int value, String nameValue)
        {
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }
    }

    /**
     * 排名类型
     */
    public enum RankingType
    {

        /**
         * 评分
         */
        score,

        /**
         * 评分数
         */
        scoreCount,

        /**
         * 周点击数
         */
        weekHits,

        /**
         * 月点击数
         */
        monthHits,

        /**
         * 点击数
         */
        hits,

        /**
         * 周销量
         */
        weekSales,

        /**
         * 月销量
         */
        monthSales,

        /**
         * 销量
         */
        sales
    }

    /**
     * 参数
     */
    public enum Attribute
    {

        /**
         * 服装版型
         */
        clothing(0, "服装版型"),

        /**
         * 袖长
         */
        sleeved(1, "袖长"),

        /**
         * 袖型
         */
        sleevedStyle(2, "袖型"),

        /**
         * 领型
         */
        collar(3, "领型"),

        /**
         * 门襟
         */
        placket(4, "门襟"),

        /**
         * 裤装版型
         */
        pantsStyle(0, "裤装版型"),

        /**
         * 裤长
         */
        pantsLong(1, "裤长"),

        /**
         * 腰高型
         */
        waistType(2, "腰高型"),

        /**
         * 款式细节
         */
        styleInfo(3, "款式细节"),

        /**
         * 轮廓
         */
        dressContour(0, "连衣裙轮廓"),

        /**
         * 连衣裙长
         */
        dressLong(1, "连衣裙长"),

        /**
         * 连衣裙款式
         */
        dressStyle(2, "连衣裙款式"),

        /**
         * 连衣裙腰型
         */
        dressWaist(3, "连衣裙腰型"),

        /**
         * 连衣裙门襟
         */
        dressPlacket(4, "连衣裙门襟"),

        /**
         * 连衣裙裙型
         */
        dressType(5, "连衣裙裙型"),

        /**
         * 连衣裙袖长
         */
        dressSleeveLong(6, "连衣裙袖长"),

        /**
         * 连衣裙袖型
         */
        dressSleeveStyle(7, "连衣裙袖型"),

        /**
         * 连衣裙领型
         */
        dressCollar(8, "连衣裙领型"),

        /**
         * 套装类型
         */
        suitType(0, "套装类型"),

        /**
         * 套装用途
         */
        suitUse(1, "套装用途"),

        /**
         * 套装数量
         */
        suitNum(2, "套装数量"),

        /**
         * 轮廓
         */
        skirtContour(0, "短裙轮廓"),

        /**
         * 裙长
         */
        skirtLong(1, "短裙裙长"),

        /**
         * 裙子款式
         */
        skirtStyle(2, "短裙款式"),

        /**
         * 裙子腰型
         */
        skirtWaist(3, "短裙腰型");

        private int value;

        private String nameValue;

        private Attribute(int value, String nameValue)
        {
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }
    }

}