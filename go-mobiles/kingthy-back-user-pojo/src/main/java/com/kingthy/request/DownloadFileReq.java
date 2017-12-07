package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * DownloadFileReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class DownloadFileReq implements Serializable
{
    private static final long serialVersionUID = -9078734818848659684L;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("文件路径,url地址")
    private String fileurl;

    @ApiModelProperty("加密key")
    private String encryptKey;

    @ApiModelProperty("文件id，在服务器保存的文件id")
    private String fileId;

    @ApiModelProperty("文件是否需要解密")
    private Boolean isDecrypt;

}
