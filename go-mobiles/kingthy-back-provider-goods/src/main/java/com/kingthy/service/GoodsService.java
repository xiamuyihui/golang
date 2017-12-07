package com.kingthy.service;

import java.util.List;

import com.kingthy.request.*;
import org.springframework.data.domain.Page;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.BuyersShowListDTO;
import com.kingthy.dto.GoodsDTO;
import com.kingthy.dto.GoodsOrderDTO;
import com.kingthy.entity.EsGoods;
import com.kingthy.entity.Goods;
import com.kingthy.response.WebApiResponse;

public interface GoodsService
{
    
    /**
     * 查询商品信息
     * 
     * @param goodsUuid
     * @return
     */
    Goods queryGoods(String goodsUuid);
    
    GoodsDTO queryGoods(String goodsUuid, String token) throws Exception;

    GoodsDTO.DesignerInfo parseDesignerInfo(String goodsUuid, String token) throws Exception;

    GoodsDTO.ProductInfo parseProductInfo(String goodsUuid) throws Exception;

//    List<GoodsDTO.FigureInfo> parseFigureInfo(String token) throws Exception;

    /**
     * 分页查询所有商品
     * 
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Goods> queryAllGoods(Integer pageNo, Integer pageSize);
    
    /**
     * 分页筛选商品
     * 
     * @param goods
     * @return
     */
    PageInfo<Goods> queryGoodsSelective(QueryGoodsReq goods);
    
    Page<EsGoods> queryEsGoods(QueryGoodsReq goods);
    
    /**
     * 创建一个新的商品
     * 
     * @param goods
     * @return
     */
    int createGoods(Goods goods);
    
    /**
     * 通过一个作品创建一个私有商品
     * 
     * @param uuid
     * @return
     */
    String createPrivateGoods(String uuid);
    
    /**
     * 下架商品
     * 
     * @param uuid
     * @return
     */
    int downGoods(String uuid,String desingerUuid);
    
    /**
     * 上架商品
     * 
     * @param uuid
     * @return
     */
    int upGoods(String uuid,String desingerUuid);
    
    /**
     * 删除商品
     * 
     * @param uuid
     * @return
     */
    int deleteGoods(String uuid);
    
    /**
     * 商品点击量
     * 
     * @param goodsClickReq
     * @return
     * @throws Exception
     */
    WebApiResponse goodsClick(List<GoodsClickReq> goodsClickReq);
    
    /**
     * 查询 面料和风格
     * 
     * @return
     * @throws Exception
     */
    WebApiResponse queryMaterielAndStyle();
    
    List<GoodsOrderDTO> selectGoodsListByGoodsIds(List<String> listGoodsUuid);
    
    List<BuyersShowListDTO> selectBuyersShowList(BuyersShowReq req);
    
    PageInfo<Goods> selectGoodsWarehouse(SelectGoodsByWarehouseReq selectGoodsByWarehouseReq);

    WebApiResponse esGoods(EsGoodsReq esGoodsReq) throws Exception;
}
