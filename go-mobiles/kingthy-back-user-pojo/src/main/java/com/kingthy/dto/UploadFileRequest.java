package com.kingthy.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * 文件上传请求
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class UploadFileRequest implements Serializable
{
    private static final long serialVersionUID = 3591497496519063476L;
    
    private String funcFlag;
    
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 文件 BASE64的字符串（base64 格式上传文件）
     */
    private String fileData;

    /**
     * 文件二进制数据（二进制格式上传文件）
     */
    private byte[]  fileByte;
    
    @ApiModelProperty("文件标识，拆分文件上传时必须")
    private String fileId;
    
    @ApiModelProperty("文件总大小，拆分文件上传时必须")
    private Integer totalSize;
}