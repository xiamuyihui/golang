package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description: 缝合关系接口表
 * @DATE Created by 14:24 on 2017/9/26.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoStitchingStyleDTO extends BaseReq implements Serializable
{
    @ApiModelProperty("子订单号")
    private String orderItemSn;

    @ApiModelProperty("缝合关系接口表")
    private List<StitchingInfo> stitchingInfoList;

    @Data
    @ToString
    public static class StitchingInfo implements Serializable{
        @ApiModelProperty("缝合工艺编码")
        private String stitchingCode;

        @ApiModelProperty("工艺描述")
        private String stitchingDesc;

        @ApiModelProperty("对应裁片编码 多个以“，”号隔开")
        private String piecesCodes;

        @ApiModelProperty("对应零部件编码 多个以“，”号隔开")
        private String partsCodes;

        @ApiModelProperty("对应的标准工艺编码 多个以“，”号隔开")
        private String standardCodes;
    }

}
