package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * GoodsTagsDto(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月30日 16:54
 *
 * @version 1.0.0
 */
@Data
@ToString
public class GoodsTagsDto implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String uuid;

    private String name;
}
