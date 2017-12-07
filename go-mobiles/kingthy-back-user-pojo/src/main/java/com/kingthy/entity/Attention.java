package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 我的关注 [实体类]
 * 
 * @author likejie 2017-5-4
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class Attention  implements Serializable
{
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("会员业务主键")
    private String memberUuid;
    
    @ApiModelProperty("关注对象的uuid")
    private String attentionUuid;

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modifier;
    
}
