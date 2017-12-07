package com.kingthy.entity;

import java.io.Serializable;
import com.kingthy.common.BaseEntity;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * 人体模型 [实体类]
 * 
 * @author likejie  2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class BodyModel extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("业务主键")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
	private String uuid;

	@ApiModelProperty("会员业务主键")
	private String memberUuid;

	@ApiModelProperty("模型名称")
	private String modelName;

	@ApiModelProperty("三维模型文件")
	private String modelFile;

	@ApiModelProperty("图片文件")
	private String modelImage;

	@ApiModelProperty("是否默认")
	private Boolean isDefault;
}
