package com.kingthy.entity;



import java.io.Serializable;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
/**
 *
 * 角色菜单 [实体类]
 * 
 * @author likejie  2017-9-8
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class SysRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Integer id;

	@ApiModelProperty("运营平台角色主键")
	private String roleUuid;

	@ApiModelProperty("运营平台菜单主键")
	private String menuUuid;

}
