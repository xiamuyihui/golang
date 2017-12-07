package com.kingthy.request;

import com.kingthy.common.BaseReq;
import com.kingthy.dto.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * createGoodsReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月11日 17:43
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreateGoodsReq extends BaseReq implements Serializable
{

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品卖点")
    private String goodsFeature;

    @ApiModelProperty(value = "标准价格")
    private BigDecimal standardPrice;

    @ApiModelProperty(value = "关联作品编号")
    private String opusSn;

    @ApiModelProperty(value = "设计师UUID")
    private String desinger;

    @ApiModelProperty(value = "返还积分")
    private String returnPoint;

    @ApiModelProperty(value = "上架状态")
    private Integer status;

    @ApiModelProperty(value = "商品品类主键")
    private String goodsCategoryUuid;

    @ApiModelProperty(value = "商品风格主键")
    private String goodsStyleUuid;

    @ApiModelProperty(value = "商品详情")
    private String goodsDetails;

    @ApiModelProperty(value = "关联作品的uuid")
    private String opusUuid;

    @ApiModelProperty(value = "商品图片")
    private List<GoodsImageDto> goodsImage;

    @ApiModelProperty(value = "部件详情")
    private List<PartInfoDto> partInfo;

    @ApiModelProperty(value = "面料详情")
    private List<MaterieDto> materielInfo;

    @ApiModelProperty(value = "辅料详情")
    private List<AccessorieDto> accessoriesInfo;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "商品参数属性")
    private GoodsAttribute attribute;

    @ApiModelProperty(value = "年龄分段参数")
    private String ageSegmentUuid;

    @ApiModelProperty(value = "商品类型")
    private String goodsCategoryType;

    @ApiModelProperty(value = "模型文件地址")
    private String fileUrl;

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    public enum Type
    {

        /** 普通商品 */
        general,

        /** 兑换商品 */
        exchange,

        /** 赠品 */
        gift
    }

    /**
     * 排序类型
     */
    public enum OrderType
    {

        /** 置顶降序 */
        topDesc,

        /** 价格升序 */
        priceAsc,

        /** 价格降序 */
        priceDesc,

        /** 销量降序 */
        salesDesc,

        /** 评分降序 */
        scoreDesc,

        /** 日期降序 */
        dateDesc
    }

    public enum GoodsStatus
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:47 2017/11/2
         */
        goodsUp(1, "已上架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:47 2017/11/2
         */
        goodsDown(0, "已下架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:47 2017/11/2
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

        /** 评分 */
        score,

        /** 评分数 */
        scoreCount,

        /** 周点击数 */
        weekHits,

        /** 月点击数 */
        monthHits,

        /** 点击数 */
        hits,

        /** 周销量 */
        weekSales,

        /** 月销量 */
        monthSales,

        /** 销量 */
        sales
    }
}
