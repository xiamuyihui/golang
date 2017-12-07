package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:15 on 2017/5/27.
 * @Modified by:
 */

@Data
@ToString
public class EsGoodsRabbitDTO extends BaseRabbitMqDTO implements Serializable {

    private String goodsUuid;

}
