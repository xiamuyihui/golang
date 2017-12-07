package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name ArrangingMaterialReq
 * @description 排料入参类
 * @create 2017/10/16
 */
@ToString
@Data
public class ArrangingMaterialReq implements Serializable {

    private String uuid;

    private String exchange;

    private String routingKey;
}
