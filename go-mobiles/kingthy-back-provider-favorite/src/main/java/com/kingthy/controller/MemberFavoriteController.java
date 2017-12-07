package com.kingthy.controller;


import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.entity.MemberFavorite;
import com.kingthy.request.AddMemberFavoriteReq;
import com.kingthy.request.MemberFavoritePageReq;
import com.kingthy.request.MemberFavoriteReq;
import com.kingthy.response.MemberFavoriteResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.MemberFavoriteService;
import com.kingthy.service.impl.MemberFavoriteServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author :pany
 * @date:2016-11-3
 *
 */
@Api(description = "收藏夹接口")
@RestController
@RequestMapping("/member")
public class MemberFavoriteController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberFavoriteServiceImpl.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private MemberFavoriteService memberFavoriteService;

    @Autowired
    DiscoveryClient client;

    @ApiOperation(value = "收藏夹列表")
    @GetMapping(value = "/query")
    public WebApiResponse<?> queryFavorite(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try {
            ServiceInstance serviceInstance = client.getLocalServiceInstance();
            LOGGER.info("/query,host:" + serviceInstance.getHost() + ",service port:" + serviceInstance.getPort());

            String memberUuid = redisManager.get(token);
            if(StringUtils.isBlank(memberUuid)){
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            List<MemberFavoriteResp> favoriteResps = memberFavoriteService.getMemberFavorites(memberUuid);
            return WebApiResponse.success(favoriteResps);

        }catch (Exception ex){
            LOGGER.error("queryFavorite error:"+ex.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }

    }
    @ApiOperation(value = "分页查询搜藏列表", notes = "分页查询搜藏列表")
    @PostMapping(value = "/pageGetMemberFavoriteList")
    public WebApiResponse<?> pageGetMemberFavoriteList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody MemberFavoritePageReq req){

        try {
            String memberUuid = redisManager.get(token);
            if(StringUtils.isBlank(memberUuid)){
                return  WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            req.setMemberUuid(memberUuid);
            PageInfo<MemberFavoriteResp> pageInfo = memberFavoriteService.pageGetMemberFavoriteList(req);
            return WebApiResponse.success(pageInfo);
        }catch (Exception ex){
            LOGGER.error("pageGetMemberFavoriteList error:" + ex.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    @ApiOperation(value = "添加到收藏夹", notes = "")
    @PostMapping(value = "/add")
    public WebApiResponse<?> addFavorite(

            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @RequestBody @ApiParam(name = "favoriteRequest", value = "收藏夹", required = true) AddMemberFavoriteReq favoriteRequest) {
        try {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(favoriteRequest.getFavoriteGoodsUuid())) {
                return WebApiResponse.error(ResponseMsg.PARAMETER_ERROR.getValue());
            }
            MemberFavorite memberFavorite = new MemberFavorite();
            memberFavorite.setFavoriteMembersUuid(memberUuid);
            memberFavorite.setFavoriteGoodsUuid(favoriteRequest.getFavoriteGoodsUuid());

            if (memberFavoriteService.isFacorited(memberFavorite)) {
                return WebApiResponse.error("商品已收藏");
            }
            int result = memberFavoriteService.addMemberFavorite(memberFavorite);
            return result == 0 ? WebApiResponse.error(ResponseMsg.FAIL.getValue()) : WebApiResponse.success(ResponseMsg.SUCCESS.getValue());
        } catch (Exception ex) {
            LOGGER.error("addFavorite error:" + ex.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "移除收藏夹")
    @PutMapping(value = "/cancel")
    public WebApiResponse<?> cancelFavorite(

            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @RequestBody @ApiParam(name = "memberFavoriteReq", value = "收藏夹", required = true) MemberFavoriteReq memberFavoriteReq)
    {
        try {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            memberFavoriteReq.setMemberUuid(memberUuid);
            int res=memberFavoriteService.delMemberFacorite(memberFavoriteReq);
            return res>0?WebApiResponse.success():WebApiResponse.error(ResponseMsg.FAIL.getValue());
        }catch (Exception ex){
            LOGGER.error("cancelFavorite error:"+ex.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

}
