package com.kingthy.mapper;

import com.kingthy.entity.BuyersShow;
import com.kingthy.util.MyMapper;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 17:34 on 2017/5/11.
 * @Modified by:
 */
public interface BuyersShowMapper extends MyMapper<BuyersShow> {

    String selectContentByUuid(String buyersUuid);

    int updateContentByUuid(BuyersShow buyersShow);
}
