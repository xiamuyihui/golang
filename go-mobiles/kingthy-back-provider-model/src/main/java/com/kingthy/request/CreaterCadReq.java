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
public class CreaterCadReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 模型文件路径
     */
    public String modelPath;

    /**
     * 用户uuid
     */
    public String memberUuid;

    /**
     * cad特征线文件
     */
    public String cadPath;
}
