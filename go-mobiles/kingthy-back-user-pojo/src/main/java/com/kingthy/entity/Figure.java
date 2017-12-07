package com.kingthy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**

 * Figure
 * @author zsh
 * @date  2017/6/8.
 */
@Data
@ToString
public class Figure implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("会员业务主键")
    private String memberUuid;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("修改时间")
    private Date modifyDate;

    @ApiModelProperty("版本")
    private Integer version;

    @ApiModelProperty("创建者")
    private String creator;

    @ApiModelProperty("修改者")
    private String modifier;

    @ApiModelProperty("业务主键")
    private String uuid;

    @ApiModelProperty("与会员的关系")
    private String familyRelation;

    @ApiModelProperty("是否删除")
    private Boolean delFlag;

    @ApiModelProperty("模型地址")
    private String modelPath;

    @ApiModelProperty("名称")
    private String figureName;

    @ApiModelProperty("模型名称")
    private String modelName;

    @ApiModelProperty("三维模型文件")
    private String modelFile;

    @ApiModelProperty("图片文件")
    private String modelImage;

    @ApiModelProperty("是否默认")
    private Boolean isDefault;

    @ApiModelProperty("盐，数据加密秘钥")
    private String salt;

    @ApiModelProperty("状态")
    private Integer status;

    private static final long serialVersionUID = 1L;

}