package com.kingthy.entity;


import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 潮搭实体类
 * @author
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OperateFashionCollection extends BaseTableFileds implements Serializable{


    private Boolean status;

    private String collectionName;

    private String banners;

    private String temperature;

    private String occasion;

    private String color;

    private String style;

    private String clothModel;

    private String clothModelPic;


}