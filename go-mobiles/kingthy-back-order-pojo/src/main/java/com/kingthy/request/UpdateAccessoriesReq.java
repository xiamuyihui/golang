package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * CreateMaterial(添加面料参数实体bean)
 * 
 * @author 赵生辉 2017年4月17日 下午4:46:59
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class UpdateAccessoriesReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务主键")
    private String uuid;

    @ApiModelProperty(value = "辅料编码")
    private String code;

    @ApiModelProperty(value = "辅料名称")
    private String name;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "材质")
    private String texture;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "采样供应商")
    private String supplier;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "联系电话")
    private String linktel;

    @ApiModelProperty(value = "联系手机")
    private String linkphone;

    @ApiModelProperty(value = "仿真编号")
    private String faxnum;

    @ApiModelProperty(value = "面料状态")
    private Integer status;

    @ApiModelProperty(value = "面料类型")
    private String materielUuid;

    @ApiModelProperty(value = "计量单位")
    private String measurement;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "是否缩水")
    private Boolean isShrink;

    @ApiModelProperty(value = "库存")
    private Integer isStock;

    @ApiModelProperty(value = "效果图")
    private String image;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "数据文件")
    private String dataFile;

    @ApiModelProperty(value = "贴图文件")
    private String chartletFile;
}
