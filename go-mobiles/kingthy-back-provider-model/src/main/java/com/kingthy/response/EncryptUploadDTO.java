package com.kingthy.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 文件加密上传DTO
 * @author likejie
 * @date  2017/9/11.
 */
@Data
@ToString
public class EncryptUploadDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("文件url")
    private String fileUrl;

    @ApiModelProperty("加密key")
    private String encryptKey;
}
