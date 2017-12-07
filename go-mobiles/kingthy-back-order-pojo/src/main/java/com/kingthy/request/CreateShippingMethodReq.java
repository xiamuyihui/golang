package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryShippingMethodPage(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月17日 13:51
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreateShippingMethodReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 快递公司的业务主键
     */
    private String deliveryCorpUuid;

    /**
     * 状态
     */
    private Integer type;
}
