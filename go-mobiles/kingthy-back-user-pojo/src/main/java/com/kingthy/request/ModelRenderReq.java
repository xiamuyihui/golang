package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 模型渲染
 * @author likejie
 * @date  2017/10/23.
 *
 */
@Data
@ToString
public class ModelRenderReq implements Serializable {

    @ApiModelProperty("用户token")
    private String token;

    @JsonIgnore
    private String memberUuid;

    @ApiModelProperty("文件二进制数据")
    private byte[] filelData;

    @ApiModelProperty("文件扩展名：.txt｜.pdf｜.jpg｜...")
    private String extName;

    @ApiModelProperty("是否需要解压缩")
    private boolean isUnzip=true;
}
