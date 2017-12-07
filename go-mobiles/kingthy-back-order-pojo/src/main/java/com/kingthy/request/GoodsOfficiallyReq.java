package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:51 on 2017/7/26.
 * @Modified by:
 */

@Data
@ToString
public class GoodsOfficiallyReq implements Serializable {

    @ApiModelProperty("商品分类uuid")
    private String goodsCategoryUuid;
    @ApiModelProperty("商品类型")
    private String goodsCategoryType;
    @ApiModelProperty("设计师uuid")
    private String designer;
    @ApiModelProperty("设计师")
    private String designerName;
    @ApiModelProperty("模型文件url")
    private String fileUrl;
    @ApiModelProperty("商品属性")
    private String attribute;
    @ApiModelProperty("封面图")
    private String cover;
    @ApiModelProperty("会员ID")
    private String memberUuid;
    @ApiModelProperty("商品主图")
    private List<GoodsImage> goodsImages;
    @ApiModelProperty("商品详情")
    private String goodsDetails;
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("上架状态：0：未上架，1：已上架")
    private Integer status;
    private String opusUuid;

    /**
     * 商品主图
     */
    @Data
    @ToString
    public static class GoodsImage
    {
        private String opusImage;
        private String maxImg;
        private String minImg;
        private String midImg;
        @ApiModelProperty("是否是封面图")
        private boolean isCover;
        @ApiModelProperty("类型 0图片 1视频")
        private Integer type;
        @ApiModelProperty("视频地址")
        private String video;
        @ApiModelProperty("视频封面")
        private String videoImg;
        @ApiModelProperty("下标 位置")
        private String index;
    }

    @Data
    @ToString
    public static class UpOrDownBatch implements Serializable{

        private Integer status;
        private Date modifyDate;
        private String modifier;
        private List<String> uuidArray;
    }
}
