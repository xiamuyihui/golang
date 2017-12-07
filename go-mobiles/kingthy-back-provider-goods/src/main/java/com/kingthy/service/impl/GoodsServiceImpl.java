package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.cache.RedisManager;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.*;
import com.kingthy.dubbo.basedata.service.MaterielCategoryDubboService;
import com.kingthy.dubbo.basedata.service.StyleCategoryDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.opus.service.OpusDubboService;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.dubbo.review.service.BuyersShowDubboService;
import com.kingthy.dubbo.review.service.LikesDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.dubbo.user.service.MemberFavoriteDubboService;
import com.kingthy.entity.*;
import com.kingthy.exception.BizException;
import com.kingthy.repository.EsGoodsRepository;
import com.kingthy.request.*;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GoodsQueryService;
import com.kingthy.service.GoodsService;
import com.kingthy.service.OpusService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service(value = "productService")
public class GoodsServiceImpl implements GoodsService
{

    private static final Logger LOG = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0")
    private MemberFavoriteDubboService memberFavoriteDubboService;

    @Reference(version = "1.0.0")
    private BuyersShowDubboService buyersShowDubboService;

    @Reference(version = "1.0.0")
    private StyleCategoryDubboService styleCategoryDubboService;

    @Reference(version = "1.0.0")
    private MaterielCategoryDubboService materielCategoryDubboService;

    @Reference(version = "1.0.0")
    private LikesDubboService likesDubboService;

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0")
    private OpusDubboService opusDubboService;

    @Reference(version = "1.0.0")
    private SnDubboService snDubboService;

    @Autowired
    private OpusService opusService;

    @Autowired
    private GoodsQueryService goodsQueryService;

    @Autowired
    private EsGoodsRepository esGoodsRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisManager redisManager;

    private static final int VERSION = 1;

    /**
     * 获取sn
     *
     * @return
     */
    private String getSn()
    {

        WebApiResponse<String> sn = snDubboService.generateSn(Sn.Type.goods.name());
        return sn.getCode() != WebApiResponse.SUCCESS_CODE ? null : sn.getData();
    }

    private String getMember(String token)
    {

        return stringRedisTemplate.opsForValue().get(token);
    }

    @Override
    public Goods queryGoods(String goodsUuid)
    {
        return goodsDubboService.selectByUuid(goodsUuid);
    }

    private GoodsDTO.GoodsInfo parseGoodsInfo(Goods goods, String memberUuid)
    {

        String goodsUuid = goods.getUuid();

        GoodsDTO.GoodsInfo goodsInfo = new GoodsDTO.GoodsInfo();
        goodsInfo.setFreight(new BigDecimal(0));
        goodsInfo.setPrice(goods.getStandardPrice());
        goodsInfo.setGoodsName(goods.getGoodsName());
        goodsInfo.setProductionCycle(206L);
        goodsInfo.setShrinkageMap(goods.getCover());
        goodsInfo.setUuid(goodsUuid);

        Long monthSale = goodsQueryService.saleCountByGoodsUuidAndMonth(goodsUuid);
        Long favorite = goodsQueryService.selectFavoriteCountByGoodsUuidAndMembersUuid(goodsUuid, memberUuid);
        Long commentCount = goodsQueryService.selectBuyerShowCountByGoodUuid(goodsUuid);
        goodsInfo.setMonthSale(monthSale == null ? 0 : monthSale);
        goodsInfo.setFavorite((favorite == null || favorite <= 0) ? false : true);
        goodsInfo.setCommentCount(Long.valueOf(commentCount));

        return goodsInfo;
    }

    //@HystrixCommand(fallbackMethod = "queryGoodsFallback")
    @Override
    public GoodsDTO queryGoods(String goodsUuid, String token) throws Exception
    {
//        Goods goods = goodsDubboService.selectByUuid(goodsUuid);
        Goods goods = goodsDubboService.queryGoodsByUUID(goodsUuid);

        if (goods == null)
        {
            return null;
        }

        String memberUuid = getMember(token);

       if (StringUtils.isEmpty(memberUuid)){

            LOG.error("token 不正确 token:" + token);
            throw new BizException("token 不正确");
        }

//        ExecutorService executor = null;

        GoodsDTO goodsDTO = new GoodsDTO();

/*       try{
           executor = Executors.newFixedThreadPool(6);

           executor.execute(() -> parseImg(goodsDTO, goods));

           Future<GoodsDTO.GoodsInfo> goodsInfoFuture = executor.submit(() -> parseGoodsInfo(goods, memberUuid));

           Future<GoodsDTO.DesignerInfo> designerInfoFuture = executor.submit(() -> parseDesignerInfo(memberUuid, goods));

           Future<List<GoodsDTO.FigureInfo>> listFuture = executor.submit(() -> parseFigureInfo(memberUuid));

           Future<GoodsDTO.ProductInfo> productInfoFuture = executor.submit(() -> parseProductInfo(goods));

           Future<List<GoodsDTO.BuyersShowInfo>> buyershowList = executor.submit(() -> parseBuyersShow(goods.getUuid()));

           goodsDTO.setGoodsInfo(goodsInfoFuture.get());

           goodsDTO.setDesignerInfo(designerInfoFuture.get());

           // 体型数据(人模)
           goodsDTO.setFigureInfoList(listFuture.get());

           // 产品参数 流行元素 袖长 服装版型 领型 袖型 成分含量 面料 适合年龄 风格
           goodsDTO.setProductInfo(productInfoFuture.get());

           // 买家秀
           goodsDTO.setBuyersShowInfoList(buyershowList.get());

       }finally {
           if (executor != null){
               stop(executor);
           }
       }*/

       //封装图片
        parseImg(goodsDTO, goods);

        // 商品价格 名称 快递 生产周期 月销量
        goodsDTO.setGoodsInfo(parseGoodsInfo(goods, memberUuid));

        // 设计师名称 ID 是否关注 已发布的设计数 头像
//        goodsDTO.setDesignerInfo(parseDesignerInfo(memberUuid, goods));

        // 体型数据(人模)
//        goodsDTO.setFigureInfoList(parseFigureInfo(memberUuid));

        // 产品参数 流行元素 袖长 服装版型 领型 袖型 成分含量 面料 适合年龄 风格
//        goodsDTO.setProductInfo(parseProductInfo(goods));

        // 买家秀
//        goodsDTO.setBuyersShowInfoList(parseBuyersShow(goods.getUuid()));

        return goodsDTO;

    }

    private void parseImg(GoodsDTO goodsDTO, Goods goods){
        Gson gson = new Gson();

//        GoodsDTO goodsDTO = new GoodsDTO();

        List<EsGoods.GoodsImage> goodsImage = gson.fromJson(goods.getGoodsImage(), new TypeToken<ArrayList<EsGoods.GoodsImage>>(){}.getType());

        // 商品中图
        List<String> midImgs = new ArrayList<>(goodsImage.size());
        // 大图详情
        List<String> maxImgs = new ArrayList<>(goodsImage.size());

        goodsImage.forEach(g -> {
            midImgs.add(g.getMidImg());
            maxImgs.add(g.getMaxImg());
        });

        goodsDTO.setMaxImgs(maxImgs);
        goodsDTO.setMidImgs(midImgs);
    }

    private GoodsDTO queryGoodsFallback(String goodsUuid, String token, Throwable e)
    {
        LOG.error("queryGoods 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    public GoodsDTO.DesignerInfo parseDesignerInfo(String goodsUuid, String token) throws Exception
    {

//        Goods goods = goodsDubboService.selectByUuid(goodsUuid);
        String desinger = goodsDubboService.queryDesingerByUUID(goodsUuid);

        if (StringUtils.isEmpty(desinger))
        {
            throw new BizException("查询不到商品");
        }

        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){

            LOG.error("token 不正确 token:" + token);
            throw new BizException("token 不正确");
        }

        return goodsQueryService.selectDesignerInfoByDesignerUuid(memberUuid, desinger);
    }

    /**
     * 查询体型数据和价格
     *
     * @param
     * @return
     */
/*    @Override
    public List<GoodsDTO.FigureInfo> parseFigureInfo(String token) throws Exception
    {
        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){

            LOG.error("token 不正确 token:" + token);
            throw new BizException("token 不正确");
        }

        return goodsQueryService.parseFigureInfo(memberUuid);

    }*/

    @Override
    public GoodsDTO.ProductInfo parseProductInfo(String goodsUuid) throws Exception
    {
//        Goods goods = goodsDubboService.selectByUuid(goodsUuid);
        Goods goods = goodsDubboService.queryMaterielInfoByUUID(goodsUuid);

        if (goods == null)
        {
            throw new BizException("查询不到商品");
        }

        Gson gson = new Gson();

        List<Map<String, Object>> listMateriel =
                gson.fromJson(goods.getMaterielInfo(), new TypeToken<ArrayList<Map<String, Object>>>()
                {
                }.getType());

        StringBuilder materiel = new StringBuilder("");

        if (listMateriel != null){
            listMateriel.forEach(map -> materiel.append((String)map.get("name")));
        }

        GoodsAttribute goodsAttribute = null;

        GoodsAttribute.CoatAttribute coatAttribute = null;
        GoodsAttribute.PantsAttribute pantsAttribute = null;
        GoodsAttribute.DressAttribute dressAttribute = null;
        GoodsAttribute.SuitAttribute suitAttribute = null;
        GoodsAttribute.SkirtAttribute skirtAttribute = null;

        if (!StringUtils.isEmpty(goods.getAttribute()) && !StringUtils.isEmpty(goods.getGoodsCategoryType())){

            goodsAttribute = new GoodsAttribute();

            coatAttribute = gson.fromJson(goods.getAttribute(), GoodsAttribute.CoatAttribute.class);

            if (!StringUtils.isEmpty(coatAttribute.getClothing())){
                goodsAttribute.setCoatAttribute(coatAttribute);
            }
            //通过属性值是否为空来判断款式类型
            else if (StringUtils.isEmpty(coatAttribute.getClothing())){

                pantsAttribute = gson.fromJson(goods.getAttribute(), GoodsAttribute.PantsAttribute.class);

                goodsAttribute.setPantsAttribute(pantsAttribute);
            }
            else if (StringUtils.isEmpty(pantsAttribute.getPantsLong())){

                dressAttribute = gson.fromJson(goods.getAttribute(), GoodsAttribute.DressAttribute.class);

                goodsAttribute.setDressAttribute(dressAttribute);
            }
            else if (StringUtils.isEmpty(dressAttribute.getDressCollar())){

                suitAttribute = gson.fromJson(goods.getAttribute(), GoodsAttribute.SuitAttribute.class);

                goodsAttribute.setSuitAttribute(suitAttribute);
            }
            else if (StringUtils.isEmpty(suitAttribute.getSuitNum())){

                skirtAttribute = gson.fromJson(goods.getAttribute(), GoodsAttribute.SkirtAttribute.class);

                goodsAttribute.setSkirtAttribute(skirtAttribute);
            }
        }

        // 产品参数
        GoodsDTO.ProductInfo productInfo = new GoodsDTO.ProductInfo();
        productInfo.setPopular("立体装饰");
        productInfo.setSleeved("短袖");
        productInfo.setClothing("修身");
        productInfo.setCollar("POLO领");
        productInfo.setSleevedStyle("常规");
        productInfo.setComponent("98%级以上");
        productInfo.setMateriel(materiel.toString());

        productInfo.setFitAge("");// 设置为空字符串

        // 查询风格名称
//        StyleCategory sq = new StyleCategory();
//        sq.setUuid(goods.getGoodsStyleUuid());

        if (!StringUtils.isEmpty(goods.getGoodsStyleUuid())){

//            StyleCategory styleCategory = styleCategoryDubboService.selectOne(sq);
            String className = styleCategoryDubboService.selectClassNameByUUID(goods.getGoodsStyleUuid());

            productInfo.setStyle(className);
        }

        return productInfo;
    }

    private List<GoodsDTO.BuyersShowInfo> parseBuyersShow(String goodsUuid)
    {

        return goodsQueryService.parseBuyersShow(goodsUuid);
    }

    @Override
    public PageInfo<Goods> queryAllGoods(Integer pageNo, Integer pageSize)
    {
        Goods var = new Goods();
        return goodsDubboService.queryPage(pageNo, pageSize, var);
    }

    //@HystrixCommand(fallbackMethod = "queryGoodsSelectiveFallback")
    @Override
    public PageInfo<Goods> queryGoodsSelective(QueryGoodsReq goods)
    {

        Goods var = new Goods();
        var.setStatus(1);
        var.setDelFlag(false);
        var.setGoodsCategoryUuid(goods.getGoodsStyleUuid());
        var.setGoodsStyleUuid(goods.getGoodsStyleUuid());
        return goodsDubboService.queryPage(goods.getPageNo(), goods.getPageSize(), var);
    }

    private PageInfo<Goods> queryGoodsSelectiveFallback(QueryGoodsReq goods, Throwable e)
    {
        LOG.error("queryGoodsSelective 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    //@HystrixCommand(fallbackMethod = "queryEsGoodsFallback")
    @Override
    public Page<EsGoods> queryEsGoods(QueryGoodsReq goods)
    {

        BoolQueryBuilder filterQueryBuilders = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(goods.getGoodsName()))
        {
            filterQueryBuilders.filter(matchQuery("goodsName", goods.getGoodsName()));
        }

        if (!StringUtils.isEmpty(goods.getGoodsCategoryUuid()))
        {
            filterQueryBuilders.filter(matchQuery("goodsCategoryUuid", goods.getGoodsCategoryUuid()));

        }

        if (!StringUtils.isEmpty(goods.getGoodsStyleUuid()))
        {
            filterQueryBuilders.filter(matchQuery("goodsStyleUuid", goods.getGoodsStyleUuid()));
        }

        if (!StringUtils.isEmpty(goods.getMaterielId()))
        {
            filterQueryBuilders
                    .filter(nestedQuery("materielInfo", termQuery("materielInfo.uuid", goods.getMaterielId())));
        }

        if (!StringUtils.isEmpty(goods.getAgeMax()))
        {
            filterQueryBuilders.must(rangeQuery("fitAgeOld").lte(goods.getPriceMin()));
        }

        if (!StringUtils.isEmpty(goods.getAgeMin()))
        {
            filterQueryBuilders.must(rangeQuery("fitAgeYoung").gte(goods.getPriceMin()));
        }

        if (!StringUtils.isEmpty(goods.getPriceMax()) && !StringUtils.isEmpty(goods.getPriceMin()))
        {
            filterQueryBuilders.must(rangeQuery("standardPrice").gte(goods.getPriceMin()).lte(goods.getPriceMax()));
        }

        if (!StringUtils.isEmpty(goods.getDesinger()))
        {
            filterQueryBuilders.filter(matchQuery("desinger", goods.getDesinger()));
        }

        // 商品排序方式 0:综合 1:价格 2:销量

        // 综合排序 销量 试穿率 收藏率 点击率
        SortBuilder sortBuilderSale = SortBuilders.fieldSort("saleCount").order(SortOrder.DESC).unmappedType("Long");
        SortBuilder sortBuilderFitting =
                SortBuilders.fieldSort("fittingCount").order(SortOrder.DESC).unmappedType("Long");
        SortBuilder sortBuilderFavorite =
                SortBuilders.fieldSort("favoriteCount").order(SortOrder.DESC).unmappedType("Long");
        SortBuilder sortBuilderClicks = SortBuilders.fieldSort("clicks").order(SortOrder.DESC).unmappedType("Long");

        // 价格排序
        SortBuilder sortBuilderPrice =
                SortBuilders.fieldSort("standardPrice").order(SortOrder.DESC).unmappedType("Long");

        /*
         * SearchQuery searchQuery = new
         * NativeSearchQueryBuilder().withQuery(matchAllQuery()).withFilter(QueryBuilders.constantScoreQuery(
         * boolQueryBuilders)) .withPageable(new PageRequest(goods.getPageNo(),goods.getPageSize()))
         * .withSort(SortBuilders.fieldSort(sort).order(SortOrder.DESC)) .build()
         */

        NativeSearchQueryBuilder nativeSearchQueryBuilder =
                new NativeSearchQueryBuilder().withQuery(filterQueryBuilders)
                        // .withFilter(filterQueryBuilders)
                        .withPageable(new PageRequest(goods.getPageNo(), goods.getPageSize()));

        // 综合排序
        if (StringUtils.isEmpty(goods.getGoodsSort()) || goods.getGoodsSort().longValue() == QueryGoodsReq.sort)
        {
            nativeSearchQueryBuilder.withSort(sortBuilderSale);
            nativeSearchQueryBuilder.withSort(sortBuilderFitting);
            nativeSearchQueryBuilder.withSort(sortBuilderFavorite);
            nativeSearchQueryBuilder.withSort(sortBuilderClicks);
        }
        // 价格
        else if (goods.getGoodsSort().longValue() == QueryGoodsReq.sort_price)
        {

            // 降序
            if (!StringUtils.isEmpty(goods.getPriceSort())
                    && QueryGoodsReq.SortType.ASC.toString().equals(goods.getPriceSort()))
            {
                sortBuilderPrice.order(SortOrder.ASC);
            }

            nativeSearchQueryBuilder.withSort(sortBuilderPrice);
        }
        // 销量
        else if (goods.getGoodsSort().longValue() == QueryGoodsReq.sort_sale)
        {

            // 降序
            if (!StringUtils.isEmpty(goods.getSaleSort())
                    && QueryGoodsReq.SortType.ASC.toString().equals(goods.getPriceSort()))
            {
                sortBuilderSale.order(SortOrder.ASC);
            }

            nativeSearchQueryBuilder.withSort(sortBuilderSale);
        }

        SearchQuery searchQuery = nativeSearchQueryBuilder.build();

        /*
         * System.out.println("query--" + searchQuery.getQuery()); System.out.println("filter--" +
         * searchQuery.getFilter())
         */

        return esGoodsRepository.search(searchQuery);
    }

    private Page<EsGoods> queryEsGoodsFallback(QueryGoodsReq goods, Throwable e)
    {
        LOG.error("queryEsGoods 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Transactional
    @Override
    public int createGoods(Goods goods)
    {

        Opus opus = new Opus();
        opus.setUuid(goods.getOpusUuid());
        opus.setOpusStatus(Opus.OpusStatus.opusUnPublish.getValue());
        opus.setDelFlag(false);
        opus.setMemberUuid(goods.getCreator());
        opus.setSn(goods.getOpusSn());

        if (null == opusDubboService.selectOne(opus)){
            throw new BizException("作品不存在或不属于当前用户");
        }

        Date date = new Date();
        goods.setCreateDate(date);
        goods.setModifyDate(date);
        goods.setVersion(VERSION);
        goods.setDelFlag(false);
        int result = goodsDubboService.insert(goods);
        if (result == 0)
        {
            throw new BizException("商品发布失败");
        }
        else
        {
            // 更新作品状态
            Opus var = new Opus();
            var.setOpusStatus(Opus.OpusStatus.opusPublish.getValue());
            var.setUuid(goods.getOpusUuid());
            var.setModifier(goods.getCreator());
            if (opusDubboService.updateByUuid(var) == 0)
            {
                throw new BizException("商品发布失败：修改作品状态失败");
            }
        }
        try
        {
            // 发送消息更新销量
            pushGoodsToRabbitMq(goods);

        }
        catch (Exception e)
        {
            LOG.error("商品发布发送消息失败" + e);
        }
        return result;
    }

    @Override
    public String createPrivateGoods(String uuid)
    {
        Opus opus = new Opus();
        opus.setUuid(uuid);
        opus = opusDubboService.selectOne(opus);
        Goods goods = new Goods();
        Date date = new Date();
        goods.setStatus(Goods.GoodsStatus.goodsUp.getValue());
        goods.setGoodsName(opus.getOpusName());
        goods.setFileUrl(opus.getRemark());
        goods.setOpusSn(opus.getSn());
        goods.setOpusUuid(opus.getUuid());
        goods.setDesinger(opus.getMemberUuid());
        EsGoods.GoodsImage goodsImage = new EsGoods.GoodsImage();
        goodsImage.setCover(true);
        goodsImage.setMaxImg(opus.getOpusImage());
        goodsImage.setMidImg(opus.getOpusImage());
        goodsImage.setOpusImage(opus.getOpusImage());
        goodsImage.setMinImg(opus.getOpusImage());
        List<EsGoods.GoodsImage> goodsImageList = new ArrayList();
        goodsImageList.add(goodsImage);
        goods.setGoodsImage(JSONObject.toJSONString(goodsImageList));
        goods.setPartInfo(opus.getOpusPartsInfo());
        goods.setAccessoriesInfo(opus.getOpusAccessoriesInfo());
        goods.setMaterielInfo(opus.getOpusMaterialInfo());
        goods.setGoodsDetails(opus.getRemark());
        opus.getClassCategoryType().substring(0, 3);
        String sn = getSn();
        goods.setStandardPrice(opus.getStandardPrice());
        goods.setSn(opus.getClassCategoryType().substring(0, 3) + sn);
        goods.setCover(opus.getOpusImage());
        goods.setGoodsCategoryType(opus.getClassCategoryType());
        goods.setGoodsCategoryName(opus.getClassCategoryName());
        goods.setFileUrl(opus.getModlePath());
        goods.setOfficiallyFlag(false);
        goods.setIsPrivate(true);
        goods.setCreateDate(date);
        goods.setModifyDate(date);
        goods.setModifier(opus.getModifier());
        goods.setCreator(opus.getCreator());
        goods.setVersion(VERSION);
        goods.setDelFlag(false);
        return goodsDubboService.insertReturn(goods);
    }

    @Transactional
    @Override
    public int upGoods(String uuid,String desingerUuid)
    {
        Goods goods = new Goods();
        goods.setUuid(uuid);
        goods.setDesinger(desingerUuid);
        if(null == goods)
        {
            throw new BizException("该商品不存在或不属于当前用户");
        }
        else if(goods.getStatus() == Goods.GoodsStatus.goodsDown.getValue())
        {
            throw new BizException("该商品已经下架,不能重复下架");
        }
        goods.setStatus(Goods.GoodsStatus.goodsUp.getValue());
        int result = goodsDubboService.updateByUuid(goods);
        if (result == 0)
        {
            throw new BizException("商品上架失败");
        }

        // 发送消息更新销量
        pushGoodsToRabbitMq(goods);

        return result;
    }

    @Transactional
    @Override
    public int downGoods(String uuid,String desingerUuid)
    {
        Goods goods = new Goods();
        goods.setUuid(uuid);
        goods.setDesinger(desingerUuid);
        goods = goodsDubboService.selectOne(goods);

        if(null == goods)
        {
            throw new BizException("该商品不存在或不属于当前用户");
        }
        else if(goods.getStatus() == Goods.GoodsStatus.goodsDown.getValue())
        {
            throw new BizException("该商品已经下架,不能重复下架");
        }
        goods.setStatus(Goods.GoodsStatus.goodsDown.getValue());
        int result = goodsDubboService.updateByUuid(goods);
        if (result == 0)
        {
            throw new BizException("商品下架失败");
        }

        // 发送消息更新销量

        return result;
    }

    private void pushGoodsToRabbitMq(Goods goods){

        EsGoodsRabbitDTO goodsRabbitDTO = new EsGoodsRabbitDTO();
        goodsRabbitDTO.setGoodsUuid(goods.getUuid());
        goodsRabbitDTO.setCreateDate(new Date());
        rabbitTemplate.convertAndSend(RabbitmqConstants.EsGoodsEnum.EXCHANGE.getValue(),
                RabbitmqConstants.EsGoodsEnum.ROUTING.getValue(),
                goodsRabbitDTO);
    }

    @Transactional
    @Override
    public int deleteGoods(String uuid)
    {
        Goods goods = new Goods();
        goods.setDelFlag(true);

        Goods originGoods = goodsDubboService.selectOne(goods);
        int result = goodsDubboService.updateByUuid(goods);
        if (result == 0)
        {
            throw new BizException("商品删除失败");
        }
        else
        {
            WebApiResponse webApiResponse = null;
            try
            {
                webApiResponse =
                        opusService.updateOpus(originGoods.getOpusUuid(), Opus.OpusStatus.opusUnPublish.getValue());
            }
            catch (Exception e)
            {
                LOG.error(e.getMessage());
                throw new BizException("删除失败");
            }
            if (webApiResponse.getCode() == 1)
            {
                throw new BizException("删除失败");
            }
        }

        // 发送消息更新销量
        pushGoodsToRabbitMq(goods);

        return result;
    }

    /**
     * 商品点击量
     *
     * @param goodsClickReq
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> goodsClick(List<GoodsClickReq> goodsClickReq)
    {

        if (goodsClickReq.isEmpty())
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        List<String> listGoodsUuid = new ArrayList<>();
        // 封装每个商品的点击数
        Map<String, Integer> map = new HashMap<>();

        for (GoodsClickReq req : goodsClickReq)
        {
            listGoodsUuid.add(req.getGoodsUuid());
            map.put(req.getGoodsUuid(), req.getClickCount());
        }

        // 查询版本号
        List<Goods> goodsList = goodsDubboService.selectVersionByGoodsUuid(listGoodsUuid);

        // 修改点击量
        for (Goods goods : goodsList)
        {
            Integer clicks = map.get(goods.getUuid());
            goods.setClicks(clicks.longValue());
            int result = goodsDubboService.updateGoodsClicks(goods);

            if (result > 0)
            {
                // 发送消息更新销量
                pushGoodsToRabbitMq(goods);
            }
        }

        return WebApiResponse.success();
    }

    /**
     * 查询 面料和风格
     *
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> queryMaterielAndStyle()
    {

        MaterielAndStyleDTO dto = new MaterielAndStyleDTO();

        MaterielCategory var2 = new MaterielCategory();
        var2.setStatus(true);
        var2.setDelFlag(false);
        var2.setGrade(1);
        dto.setMateriel(materielCategoryDubboService.select(var2).stream().map(materiel -> {
            MaterielDTO mt = new MaterielDTO();
            mt.setMaterielName(materiel.getClassName());
            mt.setMaterielUuid(materiel.getUuid());
            return mt;
        }).collect(Collectors.toList()));

        StyleCategory var1 = new StyleCategory();
        var1.setDelFlag(false);
        var1.setGrade(1);
        var1.setStatus(true);

        dto.setStyle(styleCategoryDubboService.select(var1).stream().map(sty -> {
            StyleDTO styleDTO = new StyleDTO();
            styleDTO.setStyleName(sty.getClassName());
            styleDTO.setStyleUuid(sty.getUuid());
            return styleDTO;
        }).collect(Collectors.toList()));

        return WebApiResponse.success(dto);
    }

    @HystrixCommand(fallbackMethod = "selectGoodsListByGoodsIdsFallback")
    @Override
    public List<GoodsOrderDTO> selectGoodsListByGoodsIds(List<String> listGoodsUuid)
    {
        return goodsDubboService.selectGoodsListByGoodsIds(listGoodsUuid);
    }

    private List<GoodsOrderDTO> selectGoodsListByGoodsIdsFallback(List<String> listGoodsUuid, Throwable e)
    {

        LOG.error("selectGoodsListByGoodsIds 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    /**
     * 分页查询商品的买家秀
     *
     * @param req
     * @return
     */
    @HystrixCommand(fallbackMethod = "selectBuyersShowListFallback")
    @Override
    public List<BuyersShowListDTO> selectBuyersShowList(BuyersShowReq req)
    {

        if (req.getPageNo() == null)
        {
            req.setPageNo(0);
        }
        if (req.getPageSize() == null)
        {
            req.setPageSize(10);
        }

        String memberUuid = getMember(req.getToken());
        req.setMemberUuid(memberUuid);

        int pageNo = req.getPageNo();
        int pageSize = req.getPageSize();
        int offset = (pageNo <= 0 ? pageNo : pageNo - 1) * pageSize;
        req.setPageNo(offset);

        List<BuyersShowListDTO> list = buyersShowDubboService.selectBuyersShowList(req);

        if (list.isEmpty())
        {
            return list;
        }

        ExecutorService executor = null;

        try
        {

            executor = Executors.newFixedThreadPool(2);

            List<String> listMomentUuid = list.stream().map(dto -> dto.getBuyersUuid()).collect(Collectors.toList());

            Future<List<BuyersShowListDTO.LikesDTO>> futureLikeCount =
                    executor.submit(() -> likesDubboService.selectLikeCountByMomentUuidList(listMomentUuid));

            // 查询当前用户是否已经点赞
            Future<List<BuyersShowListDTO.LikesDTO>> futureIsLike = executor
                    .submit(() -> likesDubboService.selectLikesByMomentUuidAndMemberUuid(listMomentUuid, memberUuid));

            Map<String, Long> mapLikeCount = futureLikeCount.get().stream().collect(
                    Collectors.toMap(BuyersShowListDTO.LikesDTO::getMomentUuid, likesDTO -> likesDTO.getLikeCount()));

            Set<String> stringSet =
                    futureIsLike.get().stream().map(dto -> dto.getMomentUuid()).collect(Collectors.toSet());

            list.forEach(dto -> {
                dto.setLikeCount(mapLikeCount.get(dto.getBuyersUuid()));
                dto.setIsLiked(stringSet.contains(dto.getBuyersUuid()));
            });

        }
        catch (InterruptedException e)
        {

            LOG.error(" task interrupted " + e);
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException e)
        {

            LOG.error(" 线程池异常 " + e);

        }
        finally
        {
            if (null != executor)
            {
                stop(executor);
            }
        }

        return list;
    }

    private List<BuyersShowListDTO> selectBuyersShowListFallback(BuyersShowReq req, Throwable e)
    {
        LOG.error("selectBuyersShowList 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "selectGoodsWarehouseFallback")
    @Override
    public PageInfo<Goods> selectGoodsWarehouse(SelectGoodsByWarehouseReq selectGoodsByWarehouseReq)
    {
        String memberUuid = getMember(selectGoodsByWarehouseReq.getToken());
        Goods var = new Goods();
        var.setDesinger(memberUuid);
        var.setStatus(selectGoodsByWarehouseReq.getStatus());
        return goodsDubboService.queryPage(selectGoodsByWarehouseReq.getPageNo(),
                selectGoodsByWarehouseReq.getPageSize(),
                var);
    }

    @Override
    public WebApiResponse esGoods(EsGoodsReq esGoodsReq) throws Exception {

        goodsDubboService.queryUUidByEsGoods(esGoodsReq).forEach(es -> {

            Goods goods = new Goods();
            goods.setUuid(es);
            pushGoodsToRabbitMq(goods);

        });

        return WebApiResponse.success();
    }

    private PageInfo<Goods> selectGoodsWarehouseFallback(SelectGoodsByWarehouseReq selectGoodsByWarehouseReq,
                                                         Throwable e)
    {
        LOG.error("selectGoodsWarehouse 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    /**
     * 关闭线程池
     *
     * @param executor
     */
    static void stop(ExecutorService executor)
    {
        try
        {

            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);

        }
        catch (InterruptedException e)
        {
            LOG.error("线程中断异常:" + e.getMessage());
            Thread.currentThread().interrupt();
        }
        finally
        {

            if (!executor.isTerminated())
            {
                LOG.error("杀死未完成的任务");
            }

            executor.shutdownNow();
        }

    }

}
