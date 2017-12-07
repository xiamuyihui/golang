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
public class IfoOrderDetailBomDTO extends BaseReq implements Serializable
{
    @ApiModelProperty("子订单号")
    private String orderItemSn;

    @ApiModelProperty("以面料，里布，朴布，辅料为维度的bom清单")
    private List<BomInfo> bomInfoList;

    @Data
    @ToString
    public static class BomInfo implements Serializable{

        @ApiModelProperty("款式编码")
        private String styleCode;

        @ApiModelProperty("部件类别编码")
        private String componentTypeCode;

        @ApiModelProperty("部件类别名称")
        private String componentTypeName;

        @ApiModelProperty("零部件编码")
        private String partsCode;

        @ApiModelProperty("零部件名称")
        private String partsName;

        @ApiModelProperty("物料类别")
//        private String materialTypeCode;
        private String instanceType;

        @ApiModelProperty("物料编码")
        private String materialCode;

        @ApiModelProperty("物料名称")
        private String materialName;

        @ApiModelProperty("数量")
        private Integer quantity;

        @ApiModelProperty("数量单位")
        private String unit;

        @ApiModelProperty("裁片编码")
        private String piecesCode;

        @ApiModelProperty("裁片名称")
        private String piecesName;

        @ApiModelProperty("裁片路径")
        private String piecesPath;

        @ApiModelProperty("是否需要实样")
        private Boolean factFlag;

        @ApiModelProperty("零部件纸样图路径")
        private String partsPatternPath;

        @ApiModelProperty("零部件文件路径")
        private String partsPath;
    }

}
