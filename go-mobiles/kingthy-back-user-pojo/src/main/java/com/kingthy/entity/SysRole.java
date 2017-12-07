package com.kingthy.entity;


import java.util.*;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
/**
 *
 * 系统角色 [实体类]
 * 
 * @author likejie  2017-9-8
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("业务主键")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
	private String uuid;

	@ApiModelProperty("角色名")
	private String roleName;

	@ApiModelProperty("角色描述")
	private String description;

	@ApiModelProperty("创建人")
	private String creator;

	@ApiModelProperty("创建时间")
	private Date createDate;

	@ApiModelProperty("修改人")
	private String modifier;

	@ApiModelProperty("修改时间")
	private Date modifyDate;

	@ApiModelProperty("版本")
	private Integer version;

	@ApiModelProperty("删除标志")
	private Boolean delFlag;

}
