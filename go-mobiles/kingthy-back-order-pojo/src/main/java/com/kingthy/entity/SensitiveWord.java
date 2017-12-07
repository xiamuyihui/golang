package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;


/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:37 on 2017/5/12.
 * @Modified by:
 */

@Data
@ToString
public class SensitiveWord extends BaseTableFileds {

    private String word;
}
