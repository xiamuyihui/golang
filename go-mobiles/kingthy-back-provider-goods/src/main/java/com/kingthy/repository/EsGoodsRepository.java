package com.kingthy.repository;

import com.kingthy.entity.EsGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:56 on 2017/5/19.
 * @Modified by:
 */
public interface EsGoodsRepository extends ElasticsearchRepository<EsGoods, String> {

}
