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
 * 系统用户 [实体类]
 * 
 * @author likejie  2017-9-8
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("业务主键")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
	private String uuid;

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

	@ApiModelProperty("删除标志：0：未删除，1：删除")
	private Boolean delFlag;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("电话")
	private String phone;

	@ApiModelProperty("办公电话")
	private String officePhone;

	@ApiModelProperty("电子邮件")
	private String email;

	@ApiModelProperty("盐")
	private String salt;

}
