package com.kingthy.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:20 on 2017/8/17.
 * @Modified by:
 */
@Data
@ToString
public class CreateOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ordersSn;

    private List<String> listOrderItemSn;
}
