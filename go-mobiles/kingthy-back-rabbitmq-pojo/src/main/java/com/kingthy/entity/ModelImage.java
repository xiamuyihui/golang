package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UploadModelImageReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 14:31
 *
 * @version 1.0.0
 */
@Data
@ToString
public class ModelImage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 正面照片
     */
    private String frontImage;

    /**
     * 侧面照片
     */
    private String flankImage;

    /**
     * 模型业务主键
     */
    private String modelUuid;

    /**
     * 用户的登录token
     */
    private String token;

}
