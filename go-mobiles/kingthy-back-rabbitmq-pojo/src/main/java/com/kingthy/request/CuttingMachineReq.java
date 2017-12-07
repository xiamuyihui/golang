package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name CuttingMachineReq
 * @description 裁床入参类
 * @create 2017/10/16
 */
@Data
@ToString
public class CuttingMachineReq implements Serializable {

    private String uuid;

    private String exchange;

    private String routingKey;
}
