package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 服装款式实体类
 * @author
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BaseStyle extends BaseTableFileds implements Serializable {


    private Integer status;

    private String styleSn;

    private String styleName;

    private String stylePic;

    private String season;

    private static final long serialVersionUID = 1L;

}