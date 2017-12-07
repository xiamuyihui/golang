package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Created by likejie on 2017/8/22.
 */
@ToString
@Data
public class GoodsParameter implements Serializable {

    private Integer id;
    private String goodUuid;
    private String classUuid;
    private String paramenterUuid;

}
