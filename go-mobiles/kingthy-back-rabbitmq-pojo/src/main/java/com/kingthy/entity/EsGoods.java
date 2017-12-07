package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:40 on 2017/5/19.
 * @Modified by:
 */

@Data
@ToString
//@Document(indexName = "kingthy", type = "goods", shards = 1, replicas = 1, refreshInterval = "-1")
@Document(indexName = "kingthy", type = "goods")
public class EsGoods implements Serializable {

    @Id
    private String uuid;

    private String createDate;

    private String goodsName;
    /**
     * 商品卖点
     */
    private String goodsFeature;
    /**
     *封面图
     */
    private String cover;
    /**
     * 点击量
     */
    private Long clicks;
    /**
     * 标准价格
     */
    private BigDecimal standardPrice;
    /**
     * 风格
     */
    private String goodsStyleUuid;
    /**
     * 面料 材质
     */
    @Field(type = FieldType.Nested)
    private List<MaterielInfo> materielInfo;
    /**
     * 图片
     */
    private List<GoodsImage> goodsImage;
    /**
     * 商品品类主键 (上衣，裤装，裙装，套装)
     */
    private String goodsCategoryUuid;
    /**
     * 适合年龄
     */
    private Long fitAgeYoung;
    /**
     * 适合年龄
     */
    private Long fitAgeOld;
    /**
     * 收藏次数
     */
    private Long favoriteCount;
    /**
     * 试穿率
     */
    private Long fittingCount;
    /**
     * 销量
     */
    private Long saleCount;
    /**
     * 设计师
     */
    private String desinger;

    @Data
    @ToString
    public static class MaterielInfo implements Serializable{
        private String uuid;
        private String name;
        private List<Image> images;
    }


    @Data
    @ToString
    public static class GoodsImage implements Serializable{

        private String opusImage;
        private String maxImg;
        private String minImg;
        private String midImg;
        private boolean isCover;
    }
}
