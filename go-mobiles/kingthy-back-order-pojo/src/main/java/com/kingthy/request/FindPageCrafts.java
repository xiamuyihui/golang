package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 *
 * CreateMaterial(添加面料参数实体bean)
 * 
 * @author chenz 2017年9月21日
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class FindPageCrafts implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "当前页数")
    private int pageNum;
    
    @ApiModelProperty(value = "每页显示条数")
    private int pageSize;
    
    @ApiModelProperty(value = "物料编码")
    private String code;
    
    @ApiModelProperty(value = "物料名称")
    private String name;

    @ApiModelProperty(value = "面料状态")
    private Integer status;

    @ApiModelProperty(value = "关联面料分类业务主键")
    private String materielUuid;

//    @ApiModelProperty(value = "用户token")
//    private String token;
}
