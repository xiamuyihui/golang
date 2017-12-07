package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CreateOpusReq(创建作品的)
 *
 * @author 赵生辉 2017年05月04日 16:20
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreateOpusReq implements Serializable
{
    //@NotNull(message = "作品名称不能为空")
    //@Length(min = 1, max = 60, message = "作品名称长度不合格")
    @ApiModelProperty(value = "作品名称")
    private String opusName;

    //@NotNull(message = "作品详情不能为空")
    //@Length(min = 1, max = 255, message = "作品详情长度不合格")
    @ApiModelProperty(value = "作品详情")
    private String remark;

    /*@NotNull(message = "模型地址不能为空")
    @Length(min = 1, max = 255, message = "长度不合格")*/
    @ApiModelProperty(value = "模型地址")
    private String modlePath;

    @ApiModelProperty(value = "作品状态")
    private Integer opusStatus;

    @ApiModelProperty(value = "是否显示")
    private Boolean isShow;

    //@NotNull(message = "用户业务主键不能为空")
    //@Length(min = 17, max = 17, message = "用户业务主键长度不合格")
    @ApiModelProperty(value = "用户业务主键")
    private String memberUuid;

    //@NotNull(message = "创建者不能为空")
    //@Length(min = 1, max = 25, message = "创建者长度不合格")
    @ApiModelProperty(value = "创建者")
    private String creator;

    //@NotNull(message = "修改者不能为空")
    //@Length(min = 1, max = 25, message = "修改者长度不合格")
    @ApiModelProperty(value = "修改者")
    private String modifier;

    //@NotNull(message = "作品面料详情不能为空")
    //@Length(min = 1, max = 255, message = "作品面料详情长度不合格")
    @ApiModelProperty(value = "作品面料详情")
    private String opusMaterialInfo;

    //@NotNull(message = "作品部件详情不能为空")
    //@Length(min = 1, max = 255, message = "作品部件详情长度不合格")
    @ApiModelProperty(value = "作品部件详情")
    private String opusPartsInfo;

    //@NotNull(message = "作品辅料详情不能为空")
    //@Length(min = 1, max = 255, message = "作品辅料详情长度不合格")
    @ApiModelProperty(value = "作品辅料详情")
    private String opusAccessoriesInfo;

    //@NotNull(message = "用户昵称不能为空")
    //@Length(min = 1, max = 255, message = "用户昵称长度不合格")
    @ApiModelProperty(value = "用户昵称")
    private String memberNick;

    //@NotNull(message = "适应季节不能为空")
    //@Length(min = 1, max = 25, message = "适应季节长度不合格")
    @ApiModelProperty(value = "适应季节")
    private String season;

    //@NotNull(message = "所属分类编码不能为空")
    //@Length(min = 1, max = 100, message = "长度不合格")
    @ApiModelProperty(value = "所属分类编码")
    private String classCategoryType;

    //@NotNull(message = "所属分类名称不能为空")
    //@Length(min = 1, max = 100, message = "长度不合格")
    @ApiModelProperty(value = "所属分类名称")
    private String classCategoryName;

    //@NotNull(message = "预览图不能为空")
    @ApiModelProperty(value = "预览图")
    private String opusImage;

    ///@NotNull(message = "标准价格不能为空")
    @ApiModelProperty(value = "标准价格")
    private BigDecimal standardPrice;

    private static final long serialVersionUID = 1L;
}
