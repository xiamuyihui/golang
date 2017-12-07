package com.kingthy.entity;


import java.util.*;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
/**
 *
 * 系统菜单 [实体类]
 * 
 * @author likejie  2017-9-8
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("业务主键")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
	private String uuid;

	@ApiModelProperty("父级菜单")
	private String parentUuid;

	@ApiModelProperty("菜单名")
	private String menuName;

	@ApiModelProperty("菜单描述")
	private String description;

	@ApiModelProperty("菜单地址")
	private String url;

	@ApiModelProperty("创建时间")
	private Date createDate;

	@ApiModelProperty("创建人")
	private String creator;

	@ApiModelProperty("修改时间")
	private Date modifyDate;

	@ApiModelProperty("修改人")
	private String modifier;

	@ApiModelProperty("版本")
	private Integer version;

}
