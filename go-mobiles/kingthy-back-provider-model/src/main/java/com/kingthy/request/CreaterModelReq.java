package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CreaterModelReq(描述其作用)
 * <p>
 * 赵生辉 2017年11月24日 10:14
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreaterModelReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 点云文件路径
     */
    public String PointCloudMLSTxtPath;

    /**
     * 骨骼文件路径
     */
    public String jointPointTranedTxtPath;

    /**
     * 模型文件路径
     */
    public String modelPath;

    /**
     * 用户uuid
     */
    public String memberUuid;
}
