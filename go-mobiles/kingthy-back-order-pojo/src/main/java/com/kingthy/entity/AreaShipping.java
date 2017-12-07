package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 20:07 on 2017/5/16.
 * @Modified by:
 */

@Data
@ToString
public class AreaShipping extends BaseTableFileds {

    private String areaUuid;
    private String shippingUuid;
}
