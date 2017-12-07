package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.BuyersShowListDTO;
import com.kingthy.dto.GoodsDTO;
import com.kingthy.dto.GoodsOrderDTO;
import com.kingthy.entity.EsGoods;
import com.kingthy.entity.Goods;
import com.kingthy.exception.BizException;
import com.kingthy.request.*;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GoodsQueryService;
import com.kingthy.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.kingthy.response.WebApiResponse.error;
import static com.kingthy.response.WebApiResponse.success;

@Api
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController
{
    @Autowired
    GoodsService goodsService;

    @Autowired
    private GoodsQueryService goodsQueryService;

    private static final Logger LOG = LoggerFactory.getLogger(GoodsController.class);
    
    private final String TIPS = "商品不存在";



    @ApiOperation(value = "商品详情", notes = "")
    @GetMapping("/{goodsUuid}")
    public WebApiResponse queryGoods(
        @PathVariable @ApiParam(name = "goodsUuid", value = "商品的UUID", required = true) String goodsUuid,@RequestHeader("Authorization") String token)
    {
        GoodsDTO goods = null;
        try
        {

            if (!NumberUtils.isDigits(goodsUuid) || StringUtils.isEmpty(token)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }

            if (StringUtils.isEmpty(getMemberByToken(token))){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }

            goods = goodsService.queryGoods(goodsUuid, token);

        }catch (BizException bz){
            LOG.error("/goods/{goodsUuid}/{token}:" + bz);
            return WebApiResponse.error(bz.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.error("/goods/{goodsUuid}/{token}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        
        if (null != goods)
        {
            return WebApiResponse.success(goods);
        }
        else
        {
            return WebApiResponse.error(TIPS);
        }
        
    }

    @ApiOperation(value = "商品详情买家秀", notes = "")
    @GetMapping("/buyer/{goodsUuid}")
    public WebApiResponse parseBuyersShow(@PathVariable @ApiParam(name = "goodsUuid", value = "商品的UUID", required = true) String goodsUuid,
                                    @RequestHeader("Authorization") String token){

        try {

            return WebApiResponse.success(goodsQueryService.parseBuyersShow(goodsUuid));

        }catch (Exception e){
            e.printStackTrace();
            LOG.error("/buyer/{goodsUuid}/{token}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }

    @ApiOperation(value = "设计师名称 ID 是否关注 已发布的设计数 头像", notes = "")
    @GetMapping("/designer/{goodsUuid}")
    public WebApiResponse parseDesignerInfo(@PathVariable @ApiParam(name = "goodsUuid", value = "商品的UUID", required = true) String goodsUuid,
                                    @RequestHeader("Authorization") String token){

        try {

            return WebApiResponse.success(goodsService.parseDesignerInfo(goodsUuid, token));

        }catch (Exception e){
            e.printStackTrace();
            LOG.error("/designer/{goodsUuid}/{token}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }

    @ApiOperation(value = "产品参数 流行元素 袖长 服装版型 领型 袖型 成分含量 面料 适合年龄 风格", notes = "")
    @GetMapping("/product/{goodsUuid}")
    public WebApiResponse parseProductInfo(@PathVariable @ApiParam(name = "goodsUuid", value = "商品的UUID", required = true) String goodsUuid,
                                            @RequestHeader("Authorization") String token){

        try {

            return WebApiResponse.success(goodsService.parseProductInfo(goodsUuid));

        }catch (Exception e){
            e.printStackTrace();
            LOG.error("/product/{goodsUuid}/{token}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }

    /*
    @ApiOperation(value = "体型数据(人模)", notes = "")
    @GetMapping("/figure/{goodsUuid}")
    public WebApiResponse parseFigureInfo(@PathVariable @ApiParam(name = "goodsUuid", value = "商品的UUID", required = true) String goodsUuid,
                                           @RequestHeader("Authorization") String token){

        try {

            return WebApiResponse.success(goodsService.parseFigureInfo(token));

        }catch (Exception e){
            e.printStackTrace();
            LOG.error("/figure/{goodsUuid}/{token}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }*/
    
    @ApiOperation(value = "分页获取所有商品", notes = "")
    @GetMapping("/queryAllGoods/{pageNo}/{pageSize}")
    public WebApiResponse queryAllGoods(
        @PathVariable @ApiParam(name = "pageNo", value = "pageNo", required = true) String pageNo,
        @PathVariable @ApiParam(name = "pageSize", value = "pageSize", required = true) String pageSize)
    {
        if (StringUtils.isBlank(pageNo))
        {
            return WebApiResponse.error("pageNo 不能为空");
        }
        if (StringUtils.isBlank(pageSize))
        {
            return WebApiResponse.error("pageSize 不能为空");
        }

        if (!NumberUtils.isDigits(pageNo)
                ||!NumberUtils.isDigits(pageSize)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }
        
        PageInfo<Goods> pageInfo = null;
        try
        {
            pageInfo = goodsService.queryAllGoods(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        }
        catch (Exception e)
        {
            LOG.error("/goods/queryAllGoods/{pageNo}/{pageSize}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0)
        {
            return success(pageInfo);
        }
        else
        {
            return success(pageInfo);
        }
        
    }
    
    @ApiOperation(value = "分页筛选获取所有商品", notes = "")
    @PostMapping("")
    public WebApiResponse queryGoodsSelective(@RequestBody QueryGoodsReq queryGoodsReq)
    {
        if (queryGoodsReq.getPageNo() == null)
        {
            return WebApiResponse.error("pageNo 不能为空");
        }
        if (queryGoodsReq.getPageSize() == null || queryGoodsReq.getPageSize().intValue() <= 0)
        {
            return WebApiResponse.error("pageSize 不能为空且要大于0");
        }
        
        Page<EsGoods> pageInfo = null;
        try
        {
            pageInfo = goodsService.queryEsGoods(queryGoodsReq);
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
            LOG.error("/goods/{queryGoodsReq}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        
        return success(pageInfo);
    }
    
    @ApiOperation(value = "作品申请为商品", notes = "")
    @PostMapping("/create")
    public WebApiResponse createGoods(@RequestBody CreateGoodsReq createGoodsReq,@RequestHeader("Authorization") String token)
    {
        int result = 0;
        try
        {
            if (StringUtils.isEmpty(token)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.NOT_LOGIN.getValue());
            }

            String memberUuid = getMemberByToken(token);

            if (StringUtils.isEmpty(memberUuid) || !memberUuid.equals(createGoodsReq.getDesinger())
                    || StringUtils.isEmpty(createGoodsReq.getOpusUuid()) || !NumberUtils.isDigits(createGoodsReq.getOpusUuid())
                    || createGoodsReq.getMaterielInfo() == null || createGoodsReq.getMaterielInfo().size() <= 0
                    || createGoodsReq.getAccessoriesInfo() == null || createGoodsReq.getAccessoriesInfo().size() <= 0
                    ){

                return error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }


            Goods goods = JSON.parseObject(JSON.toJSONString(createGoodsReq), Goods.class);

            goods.setCreator(memberUuid);
            result = goodsService.createGoods(goods);
        }
        catch (BizException bz){
            LOG.error("/goods/createGoods/{createGoodsReq}:" + bz);
            return WebApiResponse.error(bz.getMessage());
        }
        catch (Exception e)
        {
            LOG.error("/goods/createGoods/{createGoodsReq}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return success(result);
    }
    
    @ApiOperation(value = "作品申请为私有商品", notes = "")
    @PostMapping("/create/private/{uuid}")
    public WebApiResponse createPrivateGoods(
        @PathVariable @ApiParam(name = "uuid", value = "uuid", required = true) String uuid)
    {
        if(!NumberUtils.isDigits(uuid))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }
        String result = null;
        try
        {
            result = goodsService.createPrivateGoods(uuid);
        }
        catch (Exception e)
        {
            LOG.error("/goods/createGoods/{createGoodsReq}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if (result != null)
        {
            return success(result);
        }
        else
        {
            return error(result);
        }
    }
    
    @ApiOperation(value = "商品下架", notes = "")
    @GetMapping("/down/{uuid}")
    public WebApiResponse downGoods(@PathVariable String uuid,@RequestHeader("Authorization") String token)
    {

        int result = 0;
        try
        {

            if(!NumberUtils.isDigits(uuid))
            {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }
            String menberUuid = getMemberByToken(token);

            result = goodsService.downGoods(uuid,menberUuid);
        }
        catch (BizException bz){
            LOG.error("/goods/downGoods/{uuid}:" + bz);
            return WebApiResponse.error(bz.getMessage());
        }
        catch (Exception e)
        {
            LOG.error("/goods/downGoods/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return success(result);
    }
    
    @ApiOperation(value = "商品上架", notes = "")
    @GetMapping("/up/{uuid}")
    public WebApiResponse upGoods(@PathVariable String uuid,@RequestHeader("Authorization") String token)
    {
        int result = 0;

        try
        {

            String menberUuid = getMemberByToken(token);

            if(!NumberUtils.isDigits(uuid) || StringUtils.isEmpty(menberUuid))
            {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }

            result = goodsService.upGoods(uuid,menberUuid);
        }
        catch (BizException bz){
            LOG.error("/goods/upGoods/{uuid}:" + bz);
            return WebApiResponse.error(bz.getMessage());
        }
        catch (Exception e)
        {
            LOG.error("/goods/upGoods/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return success(result);
    }
    
    @ApiOperation(value = "删除商品", notes = "")
    @DeleteMapping("/delete/{uuid}")
    public WebApiResponse deleteGoods(@PathVariable String uuid)
    {
        if(!NumberUtils.isDigits(uuid))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }
        int result = 0;
        try
        {
            result = goodsService.deleteGoods(uuid);
        }
        catch (Exception e)
        {
            LOG.error("/goods/upGoods/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if (result != 0)
        {
            return success(result);
        }
        else
        {
            return success(result);
        }
    }
    
    @ApiOperation(value = "点击量", notes = "")
    @RequestMapping(value = "/click/{goodsUuid}", method = RequestMethod.POST)
    public WebApiResponse goodsClick(
        @RequestBody @ApiParam(value = "每日点击量", required = true) List<GoodsClickReq> goodsClickReq)
    {
        
        WebApiResponse<?> result = null;
        
        try
        {
            
            result = goodsService.goodsClick(goodsClickReq);
            
        }
        catch (Exception e)
        {
            LOG.error("-------------增加商品点击量 失败: " + e + "--------------");
            result = new WebApiResponse<>().error("增加商品点击量失败");
            LOG.error(e.toString());
        }
        
        return result;
    }
    
    @ApiOperation(value = "查询风格和材质接口", notes = "")
    @RequestMapping(value = "/materiel/style", method = RequestMethod.POST)
    public WebApiResponse queryMaterielAndStyle()
    {
        
        WebApiResponse<?> result = null;
        
        try
        {
            
            result = goodsService.queryMaterielAndStyle();
            
        }
        catch (Exception e)
        {
            LOG.error("-------------查询风格和材质 失败: " + e + "------------");
            result = new WebApiResponse<>().error("查询风格和材质 失败");
            LOG.error(e.toString());
        }
        
        return result;
    }
    
    @ApiOperation(value = "根据商品ID数组,查询商品信息", notes = "")
    @RequestMapping(value = "/query/goodList", method = RequestMethod.POST)
    public WebApiResponse selectGoodsListByGoodsIds(
        @RequestBody @ApiParam(value = "goodsIds", required = true) List<String> listGoodsUuid)
    {
        
        List<GoodsOrderDTO> list = new ArrayList<>();
        try
        {
            list = goodsService.selectGoodsListByGoodsIds(listGoodsUuid);
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
            LOG.error("/goods/query/goodList:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        
        return list.isEmpty() ? WebApiResponse.error(TIPS) : WebApiResponse.success(list);
    }
    
    @ApiOperation(value = "分页查询商品的买家秀")
    @RequestMapping(value = "/selectBuyersShowList", method = RequestMethod.POST)
    public WebApiResponse selectBuyersShowList(@RequestBody BuyersShowReq request,@RequestHeader("Authorization") String token)
    {
        
        try
        {
//            if (StringUtils.isBlank(request.getToken()))
            if (StringUtils.isBlank(token))
            {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            request.setToken(token);
            List<BuyersShowListDTO> list = goodsService.selectBuyersShowList(request);
            return WebApiResponse.success(list);
            
        }
        catch (Exception e)
        {
            LOG.error("-------------分页查询商品的买家秀 失败: selectBuyersShowList:" + e + "-------------");
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        
    }
    
    @ApiOperation(value = "查询个人的商品")
    @RequestMapping(value = "/selectGoodsByWarehouse", method = RequestMethod.POST)
    public WebApiResponse selectGoodsByWarehouse(@RequestBody SelectGoodsByWarehouseReq selectGoodsByWarehouseReq)
    {
        
        try
        {
            PageInfo<Goods> result = goodsService.selectGoodsWarehouse(selectGoodsByWarehouseReq);
            return WebApiResponse.success(result);
        }
        catch (Exception e)
        {
            LOG.error("-------------查询个人的商品列表 失败: selectGoodsByWarehouse:" + e + "-------------");
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "手动同步商品到es")
    @RequestMapping(value = "/esGoods", method = RequestMethod.POST)
    public WebApiResponse esGoods(EsGoodsReq esGoodsReq){

        WebApiResponse<?> result = null;

        try {

            result = goodsService.esGoods(esGoodsReq);

        }catch (Exception e){
            LOG.error("-------------手动同步商品到es 失败: esGoods:" + e + "-------------");
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }
}
