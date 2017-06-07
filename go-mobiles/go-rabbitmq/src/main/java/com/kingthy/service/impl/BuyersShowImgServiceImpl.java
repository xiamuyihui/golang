package com.kingthy.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.dto.OrderCommentImgDTO;
import com.kingthy.entity.BuyersShow;
import com.kingthy.entity.EsGoods;
import com.kingthy.entity.Goods;
import com.kingthy.mapper.BuyersShowImgMapper;
import com.kingthy.mapper.BuyersShowMapper;
import com.kingthy.mapper.GoodsMapper;
import com.kingthy.mapper.SensitiveWordMapper;
import com.kingthy.repository.EsGoodsRepository;
import com.kingthy.service.BuyersShowImgService;
import com.kingthy.word.SensitiveWordFilter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 14:49 on 2017/5/12.
 * @Modified by:
 */

@Service
public class BuyersShowImgServiceImpl implements BuyersShowImgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyersShowImgServiceImpl.class);

    @Autowired
    private BuyersShowImgMapper buyersShowImgMapper;

    @Autowired
    private BuyersShowMapper buyersShowMapper;

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public void saveBuyersShowImg(OrderCommentImgDTO imgDTO) throws Exception {

/*        byte[] bs = Base64.decode(imgDTO.getFileRequest().getFileData());
        if (null == bs)
        {
            LOGGER.error("无法解析客户端上传的文件. buyersUuid = " + imgDTO.getBuyersUuid());
            return ;
        }

        //上传图片
        FastDFSClient fastDfsClient = new FastDFSClient();

        String fileId = fastDfsClient.uploadFile(bs, imgDTO.getFileRequest().getFileName());

        String fileUrl = "http://192.168.1.217/" + fileId;

        BuyersShowImg buyersShowImg = new BuyersShowImg();
        buyersShowImg.setBuyersUuid(imgDTO.getBuyersUuid());
        buyersShowImg.setCreateDate(new Date());
        buyersShowImg.setCreator(imgDTO.getMemberUuid());
        buyersShowImg.setImgUrl(fileUrl);
        buyersShowImg.setDelFlag(false);

        buyersShowImgMapper.insert(buyersShowImg);*/
    }

    /**
     * 敏感数据过滤
     * @param imgDTO
     * @throws Exception
     */
    @Override
    public void sensitiveDataFilter(OrderCommentImgDTO imgDTO) throws Exception {

        String content = buyersShowMapper.selectContentByUuid(imgDTO.getBuyersUuid());

        List<String> wordList = sensitiveWordMapper.selectWordAll();

        SensitiveWordFilter filter = new SensitiveWordFilter(new HashSet<>(wordList));

        if (filter.isContaintSensitiveWord(content, SensitiveWordFilter.minMatchTYpe)) {

            String temp = filter.replaceSensitiveWord(content, SensitiveWordFilter.minMatchTYpe, "*");
            BuyersShow vo = new BuyersShow();
            vo.setContent(temp);
            vo.setModifier(imgDTO.getBuyersUuid());
            vo.setUuid(imgDTO.getBuyersUuid());

            buyersShowMapper.updateContentByUuid(vo);
        }
    }

    @Autowired
    private EsGoodsRepository repository;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void testElasticsearch(String goodsUuid){

//        String goodsUuid = "97137901346757296";
//        String goodsUuid = "97137901346757423";

//        Goods goods = goodsMapper.selectGoodsByUuid("97071061572518338");
        Goods query = new Goods();
        query.setUuid(goodsUuid);
        Goods goods = goodsMapper.selectOne(query);

        Long favoriteCount = goodsMapper.selectFavoriteCountByGoodsUuid(goodsUuid);

        Long fittingCount = goodsMapper.selectFittingCountByGoodsUuid(goodsUuid);

        Long saleCount = goodsMapper.selectSaleCountByGoodsUuid(goodsUuid);

        EsGoods esgoods = new EsGoods();

//        EsGoods.MaterielInfo materielInfo = goods.getMaterielInfo().get(0);

        Gson gson = new Gson();

//        List<EsGoods.GoodsImage> goodsImages = new ArrayList<>();

        Type type = new TypeToken<ArrayList<EsGoods.GoodsImage>>(){}.getType();

        List<EsGoods.GoodsImage> goodsImages  = gson.fromJson(goods.getGoodsImage(), type);

        Type type2 = new TypeToken<ArrayList<EsGoods.MaterielInfo>>(){}.getType();

//        List<EsGoods.MaterielInfo> materielInfos = new ArrayList<>();

        /*String sql2 = "[{\"images\":[{\"image\":\"http://192.168.1.217/group1/M00/00/16/wKgB21kQ_8mAEMPKAAId_Mq98Rg134.jpg\"}]," +
                "\"partsubStatus\":\"\",\"name\":\"黄色弹力绳\",\"uuid\":\"97071061572517922\"}]";*/
        List<EsGoods.MaterielInfo> materielInfos = gson.fromJson(goods.getMaterielInfo(), type2);
//        List<EsGoods.MaterielInfo> materielInfos = gson.fromJson(sql2, type2);

        esgoods.setUuid(goods.getUuid());
        esgoods.setCreateDate(goods.getCreateDate().getTime() + "");
        esgoods.setGoodsName(goods.getGoodsName());
        esgoods.setGoodsFeature(goods.getGoodsFeature());
        esgoods.setClicks(goods.getClicks());
        esgoods.setCover(goods.getCover());

        String [] ids = {"97071061572518322","97071061572518323","97100764777808453", "97100764777809157"};

        Random random = new Random();
        esgoods.setGoodsCategoryUuid(ids[random.nextInt(3)]);

//        esgoods.setGoodsCategoryUuid(goods.getGoodsCategoryUuid());
        esgoods.setGoodsImage(goodsImages);
        esgoods.setGoodsStyleUuid(goods.getGoodsStyleUuid());
        esgoods.setStandardPrice(goods.getStandardPrice());
        esgoods.setMaterielInfo(materielInfos);
        esgoods.setFitAgeOld(goods.getFitAgeOld() == null ? 0 : goods.getFitAgeOld());
        esgoods.setFitAgeYoung(goods.getFitAgeYoung() == null ? 0 : goods.getFitAgeYoung());
        esgoods.setFavoriteCount(favoriteCount == null ? 0 : favoriteCount);
        esgoods.setSaleCount(saleCount == null ? 0 : saleCount);
        esgoods.setFittingCount(fittingCount == null ? 0 : fittingCount);

        EsGoods tmp = repository.save(esgoods);

//        repository.delete(goodsUuid);

//        EsGoods list = repository.findOne("97137901346757423");

//        System.out.println("----------------- "+ list.toString());


        BoolQueryBuilder boolQueryBuilders = QueryBuilders.boolQuery();

        BoolQueryBuilder boolFilterBuilders = QueryBuilders.boolQuery();

//        QueryBuilders.filteredQuery()
//        boolQueryBuilders.must(QueryBuilders.existsQuery("goodsName"));97100764777808457
//        boolQueryBuilders.must(QueryBuilders.matchQuery("goodsName", "春季最新款亚麻"));
//        boolQueryBuilders.must(QueryBuilders.termQuery("goodsName", "紫装20170505测试-11111"));
//        boolQueryBuilders.must(QueryBuilders.termQuery("uuid", "97137901346757423"));
//        boolQueryBuilders.must(QueryBuilders.termQuery("cover", "http://192.168.1.217/group1/M00/00/17/wKgB2lkKg5SAb9z4AAf19tUATKk116_50x50.jpg"));

        boolFilterBuilders.must(QueryBuilders.termQuery("goodsCategoryUuid", "97100764777808606"));

        boolQueryBuilders.filter(boolFilterBuilders);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilders).withFilter(boolFilterBuilders)


        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilders)
                .withPageable(new PageRequest(0,10)).build();

        System.out.println("--------"+searchQuery.getQuery().toString());

        Page<EsGoods> page = repository.search(searchQuery);

        System.out.println("----"+page);
    }

}
