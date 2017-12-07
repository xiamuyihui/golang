/**
 * 系统项目名称
 * com.kingthy.dto
 * MyOpusDTO.java
 * 
 * 2017年5月8日-上午11:02:37
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;



/**
 *
 * 我的作品
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class MyOpusDTO implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务主键")
    private String uuid;
    
    @ApiModelProperty("作品名称")
    private String opusName;
    
    @ApiModelProperty("作品图片")
    private String opusImage;
    
    @ApiModelProperty("作品描述")
    private String opusDetails;
    
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    
}
