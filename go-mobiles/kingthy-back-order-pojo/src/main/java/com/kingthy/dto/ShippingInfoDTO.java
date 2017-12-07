package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 19:55 on 2017/5/16.
 * @Modified by:
 */
@Data
@ToString
public class ShippingInfoDTO implements java.io.Serializable{

    private String shippingName;
    private String shippingUuid;
}
