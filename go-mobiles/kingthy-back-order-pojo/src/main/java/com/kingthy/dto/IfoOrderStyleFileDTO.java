package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:30 on 2017/9/25.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoOrderStyleFileDTO extends BaseReq implements Serializable {

    @ApiModelProperty("子订单号")
    private String orderItemSn;

    @ApiModelProperty("款式文件信息")
    private List<StyleInfo> styleInfoList;

    @Data
    @ToString
    public static class StyleInfo implements Serializable{

        @ApiModelProperty("款式编码")
        private String styleCode;

        @ApiModelProperty("物料编码")
        private String materialCode;

        @ApiModelProperty("幅宽")
        private BigDecimal breadth;

        @ApiModelProperty("数量")
        private BigDecimal quantity;

        @ApiModelProperty("数量单位")
        private String unit;

        @ApiModelProperty("面积")
        private BigDecimal acreage;

        @ApiModelProperty("纸样PLT文件路径")
        private String pltPath;

        @ApiModelProperty("裁床NC文件路径")
        private String ncPath;

        @ApiModelProperty("裁床PLT文件路径")
        private String ccPltPath;
    }

}
