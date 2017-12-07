package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class QueryGoodsReq implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    private Integer pageSize;

    private Integer pageNo;

    @ApiModelProperty("商品品类主键(上衣，裤装，裙装，套装)")
    private String goodsCategoryUuid;

    @ApiModelProperty("风格")
    private String goodsStyleUuid;

    private String goodsName;

    @ApiModelProperty("材质 面料")
    private String materielId;

    @ApiModelProperty("价格区间 最小")
    private BigDecimal priceMin;
    @ApiModelProperty("价格区间 最大")
    private BigDecimal priceMax;

    @ApiModelProperty("适合年龄 最小")
    private Long ageMin;
    @ApiModelProperty("适合年龄 最大")
    private Long ageMax;

    @ApiModelProperty("商品排序方式 0:综合 1:价格 2:销量")
    private Long goodsSort;

    @ApiModelProperty("价格排序 DESC:降序  升序:ASC")
    private String priceSort;

    @ApiModelProperty("销量排序 DESC:降序  升序:ASC")
    private String saleSort;

    @ApiModelProperty("设计师ID")
    private String desinger;

    @JsonIgnore
    public static final long sort = 0;

    @JsonIgnore
    public static final long sort_price = 1;

    @JsonIgnore
    public static final long sort_sale = 2;

    public enum SortType{
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:25 2017/11/2
         */
        DESC,
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:25 2017/11/2
         */
        ASC
    }
}
