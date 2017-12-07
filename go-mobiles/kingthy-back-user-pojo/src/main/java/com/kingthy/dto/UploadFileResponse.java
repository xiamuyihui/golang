package com.kingthy.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 文件上传相应
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class UploadFileResponse implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = -8173736783028166527L;
    
    @ApiModelProperty("文件地址")
    private String fileUrl;
    
    @ApiModelProperty("文件名称")
    private String fileName;
    
    @ApiModelProperty("文件ID")
    private String fileId;
    
}
