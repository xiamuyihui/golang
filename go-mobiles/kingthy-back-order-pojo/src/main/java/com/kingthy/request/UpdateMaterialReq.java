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
public class UpdateMaterialReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务主键")
    private String uuid;

    @ApiModelProperty(value = "面料编码")
    private String code;

    @ApiModelProperty(value = "面料名称")
    private String name;

    @ApiModelProperty(value = "面料成分")
    private String component;

    @ApiModelProperty(value = "幅宽")
    private Float length;

    @ApiModelProperty(value = "克重")
    private Float weight;

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

    @ApiModelProperty(value = "材质表")
    private String materielUuid;

    @ApiModelProperty(value = "计量单位")
    private String measurement;

    @ApiModelProperty(value = "纱支")
    private String yarnCount;

    @ApiModelProperty(value = "是否缩水")
    private Boolean isShrink;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer isStock;

    @ApiModelProperty(value = "洗涤要求")
    private String washingReq;

    @ApiModelProperty(value = "效果图片")
    private String image;

    @ApiModelProperty(value = "面料颜色")
    private String color;

    @ApiModelProperty(value = "数据文件")
    private String dataFile;

    @ApiModelProperty(value = "贴图数据")
    private String chartletFile;

    @ApiModelProperty(value = "面料状态")
    private Integer status;
    
}
