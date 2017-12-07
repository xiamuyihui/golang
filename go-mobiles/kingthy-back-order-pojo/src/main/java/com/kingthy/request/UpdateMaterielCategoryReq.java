package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UpdateMaterielCategoryReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月14日 18:14
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UpdateMaterielCategoryReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String className;

    private Boolean status;

    private String description;

    private String uuid;
}
