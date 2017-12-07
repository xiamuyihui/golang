package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dto.OrderCommentImgDTO;
import com.kingthy.dubbo.basedata.service.MaterielCategoryDubboService;
import com.kingthy.dubbo.basedata.service.SensitiveWordService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.review.service.BuyersShowDubboService;
import com.kingthy.entity.BuyersShow;
import com.kingthy.entity.Goods;
import com.kingthy.entity.MaterielCategory;
import com.kingthy.repository.EsGoodsRepository;
import com.kingthy.service.BuyersShowImgService;
import com.kingthy.word.SensitiveWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:49 on 2017/5/12.
 * @Modified by:
 */

@Service
public class BuyersShowImgServiceImpl implements BuyersShowImgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyersShowImgServiceImpl.class);

    @Reference(version = "1.0.0", timeout = 6000)
    private BuyersShowDubboService buyersShowDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private SensitiveWordService sensitiveWordService;

    @Reference(version = "1.0.0")
    MaterielCategoryDubboService materielCategoryDubboService;


    @Override
    public void saveBuyersShowImg(OrderCommentImgDTO imgDTO) throws Exception {

    }

    /**
     * 敏感数据过滤
     * @param imgDTO
     * @throws Exception
     */
    @Override
    public void sensitiveDataFilter(OrderCommentImgDTO imgDTO) throws Exception {


        BuyersShow buyersShow = buyersShowDubboService.selectByUuid(imgDTO.getBuyersUuid());

        String content = buyersShow.getContent();

        List<String> wordList = sensitiveWordService.selectWordAll();

        SensitiveWordFilter filter = new SensitiveWordFilter(new HashSet<>(wordList));

        if (filter.isContaintSensitiveWord(content, SensitiveWordFilter.minMatchTYpe)) {

            String temp = filter.replaceSensitiveWord(content, SensitiveWordFilter.minMatchTYpe, "*");
            BuyersShow vo = new BuyersShow();
            vo.setContent(temp);
            vo.setModifier(imgDTO.getBuyersUuid());
            vo.setUuid(imgDTO.getBuyersUuid());
            vo.setModifyDate(new Date());
            buyersShowDubboService.updateByUuid(vo);
        }
    }

    @Autowired
    private EsGoodsRepository repository;



    @Override
    public void testElasticsearch(String goodsUuid){


    }

    @Override
    public void delElasticSearch(String goodsName) {
//        repository.delete();
    }

    @Override
    public String testinsertReturnUuid(BuyersShow buyersShow) {
        MaterielCategory materielCategory = new MaterielCategory();
        materielCategory.setClassName("xumin1231");
        return materielCategoryDubboService.insertReturnUuid(materielCategory);
    }

    @Override
    public List<BuyersShow> queryPage(BuyersShow buyersShow) {
        return buyersShowDubboService.queryPage(0,1000, buyersShow).getList();
    }

    @Override
    public List<Goods> queryGoods() {
        Goods goods = new Goods();
        goods.setDelFlag(false);
        return goodsDubboService.queryPage(50, 1000, goods).getList();
    }


    @Reference(version = "1.0.0", timeout = 300000)
    private GoodsDubboService goodsDubboService;
}
