package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UploadModelImage(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 15:39
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UploadModelImageDTO extends BaseRabbitMqDTO implements Serializable
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
     * 当前登录者的token
     */
    private String token;
}
