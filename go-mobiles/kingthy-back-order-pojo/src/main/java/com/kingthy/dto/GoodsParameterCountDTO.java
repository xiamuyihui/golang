package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author Created by likejie on 2017/8/22.
 */
@Data
@ToString
public class GoodsParameterCountDTO implements Serializable {
    private String paramenterUuid;
    private Integer count;
}
