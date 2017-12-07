package com.kingthy.controller;

import com.kingthy.common.CommonUtils;
import com.kingthy.dto.*;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OrderService;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 * @author:xumin
 * @Description:
 * @Date:11:39 2017/11/2
 */
@Api
@RestController
@RequestMapping("order")
public class OrderProviderController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProviderController.class);
    public static final String MESSAGE = "服务器出错";

    @Autowired
    private OrderService orderService;

    /**
     * 订单创建 create(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param orderDTO
     * @return 潘勇 WebApiResponse<String>
     * @exception @since 1.0.0
     */
    @ApiOperation(value = "创建订单接口", notes = "")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = WebApiResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure")})
    public WebApiResponse<?> create(@RequestBody @ApiParam(value = "订单创建") OrderDTO orderDTO,@RequestHeader("Authorization") String token)
    {

        WebApiResponse<?> result = null;

        try{

            if (orderDTO.getGoods() == null || orderDTO.getGoods().isEmpty() || StringUtils.isEmpty(token)){
                return WebApiResponse.error("参数不正确");
            }

            orderDTO.setToken(token);

            result = orderService.createOrder(orderDTO);
        }catch (Exception e){
            LOGGER.error("-------------创建订单接口 失败: "+e+ "-------------");
            return WebApiResponse.error("服务器出错");
        }

        return result;
    }


    /**
     * 修改订单状态
     * @param statusReqDTO
     * @return
     */
    @ApiOperation(value = "修改订单状态接口", notes = "")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public WebApiResponse<?> updateOrderStatus(@RequestBody @ApiParam(value = "修改订单状态") OrderStatusReqDTO statusReqDTO,
                                               @RequestHeader("Authorization") String token)
    {

        WebApiResponse<?> result = null;

        try {
            boolean flag = StringUtils.isEmpty(statusReqDTO.getStatus())
                    ||(StringUtils.isEmpty(statusReqDTO.getOrderSn())) || StringUtils.isEmpty(statusReqDTO.getOrderUuId())
                    ||StringUtils.isEmpty(token) || !NumberUtils.isDigits(statusReqDTO.getOrderUuId());
            if (flag){

                return WebApiResponse.error("参数不正确");
            }

            statusReqDTO.setToken(token);

            result = orderService.updateOrderStatus(statusReqDTO);
        }catch (Exception e){
            LOGGER.error("-------------修改订单状态 失败: "+e+ "-------------");
            result = WebApiResponse.error("服务器出错");
        }

        return result;
    }

    /**
     * 查询订单
     * @param orderSn
     * @return
     */
    @ApiOperation(value = "查询订单接口", notes = "")
    @RequestMapping(value = "/show/{orderSn}", method = RequestMethod.POST)
    public WebApiResponse<?> showSingleOrderInfo(@PathVariable @ApiParam(value = "订单号") String orderSn)
    {

        WebApiResponse<?> result = null;

        try {

            result = orderService.showSingleOrderInfo(orderSn);

        }catch (Exception e){

            LOGGER.error("-------------查询订单 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "删除订单接口", notes = "")
    @RequestMapping(value = "/del/{orderSn}", method = RequestMethod.POST)
    public WebApiResponse<?> delOrder(@PathVariable @ApiParam(value = "订单号")String orderSn,
                                      @RequestHeader("Authorization") String token)
    {

        WebApiResponse<?> result = null;

        try {

            if (StringUtils.isEmpty(orderSn)
                    ||StringUtils.isEmpty(token)){
                return WebApiResponse.error("参数不正确");
            }

            result = orderService.delOrder(orderSn, token);

        }catch (Exception e){
            LOGGER.error("-------------删除订单 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "订单评论", notes = "")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public WebApiResponse<?> comment(@RequestBody @ApiParam(value = "评论内容") OrderCommentDTO commentDTO,
                                     @RequestHeader("Authorization") String token){

        WebApiResponse<?> result = null;

        try {
            if (StringUtils.isEmpty(commentDTO.getContent())
                    ||StringUtils.isEmpty(token)
                    ||StringUtils.isEmpty(commentDTO.getOrderSn())){

                return WebApiResponse.error("参数不正确");
            }

            commentDTO.setToken(token);
            result = orderService.comment(commentDTO);
        }catch (Exception e){
            LOGGER.error("-------------订单评论 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "申请售后服务", notes = "")
    @RequestMapping(value = "/sale/service", method = RequestMethod.POST)
    public WebApiResponse<?> afterSaleService(@RequestBody @ApiParam(value = "售后服务") AfterSaleServiceDTO dto,
                                              @RequestHeader("Authorization") String token)
    {

        WebApiResponse<?> result = null;

        try {

            if (StringUtils.isEmpty(dto.getApplyServiceType())
                    ||StringUtils.isEmpty(dto.getExchangeReason())
                    ||StringUtils.isEmpty(dto.getListImg())
                    ||dto.getListImg().size() <= 0
                    ||StringUtils.isEmpty(dto.getOrderSn())
                    ||StringUtils.isEmpty(token)){
                return WebApiResponse.error("参数不正确");
            }

            dto.setToken(token);
            result = orderService.afterSaleService(dto);

        }catch (Exception e){
            LOGGER.error("-------------申请售后服务 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }
        return result;
    }

    @ApiOperation(value = "撤销售后", notes = "")
    @RequestMapping(value = "/cancel/service", method = RequestMethod.POST)
    public WebApiResponse<?> cancelSaleService(@RequestBody @ApiParam(value = "撤销售后", required = true) CancelServiceDTO dto,
                                               @RequestHeader("Authorization") String token)
    {
        WebApiResponse<?> result = null;
        try {

            if (StringUtils.isEmpty(token)||StringUtils.isEmpty(dto.getOrderSn())){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }

            dto.setToken(token);
            result = orderService.cancelSaleService(dto);

        }catch (Exception e){
            LOGGER.error("-------------撤销售后 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }
        return result;
    }

    /**
     * 关闭订单
     * @param dto
     */
    @ApiOperation(value = "关闭订单", notes = "")
    @RequestMapping(value = "/close", method = RequestMethod.DELETE)
    public WebApiResponse<?> closeOrder(@RequestBody @ApiParam(value = "关闭订单", required = true) CancelServiceDTO dto)
    {

        WebApiResponse<?> result = null;

        try {

            result = orderService.closeOrder(dto);

        }catch (Exception e){
            LOGGER.error("-------------关闭订单 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    @ApiOperation(value = "根据城市选择合适的物流", notes = "")
    @RequestMapping(value = "/query/shipping/{areaUuid}", method = RequestMethod.GET)
    public WebApiResponse<?> queryShipping(@PathVariable @ApiParam(value = "地区ID", required = true)String areaUuid)
    {
        WebApiResponse<?> result = null;
        try {

            result = orderService.queryShipping(areaUuid);

        }catch (Exception e){
            LOGGER.error("-------------根据城市选择合适的物流 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
        }
        return result;
    }

    @ApiOperation(value = "换货原因", notes = "")
    @RequestMapping(value = "/exchange/reason", method = RequestMethod.GET)
    public WebApiResponse<?> queryExchangeReason(){
        WebApiResponse<?> result = null;
        try {

            result = orderService.queryExchangeReason();

        }catch (Exception e){
            LOGGER.error("-------------查询换货原因 失败: "+e+ "-------------");
            result = WebApiResponse.error(MESSAGE);
           
        }
        return result;
    }

    /**
     * 查看售后进度
     * @param orderSn
     */
    @ApiOperation(value = "查看售后进度", notes = "")
    @RequestMapping(value = "/sale/service/detail/{orderSn}", method = RequestMethod.GET)
    public WebApiResponse<?> queryServiceDetail(@PathVariable @ApiParam(value = "订单号", required = true)String orderSn)
    {

        WebApiResponse<?> result = null;

        if (StringUtils.isEmpty(orderSn)){

            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        try {
            result = orderService.queryServiceDetail(orderSn);
        }catch (Exception e){
            LOGGER.error("-------------查询换货原因 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
           
        }

        return result;

    }

    /**
     * 查询售后列表
     * @param token
     */
    @ApiOperation(value = "查询售后列表", notes = "")
    @RequestMapping(value = "/sale/service/list/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public WebApiResponse<?> querySaleServiceList(@PathVariable @ApiParam(name = "pageNo", value = "pageNo", required = true) String pageNo,
                                                  @PathVariable @ApiParam(name = "pageSize", value = "pageSize", required = true) String pageSize,
                                                  @RequestHeader("Authorization") String token)
    {

        WebApiResponse<?> result = null;

        if (StringUtils.isEmpty(pageNo)||StringUtils.isEmpty(pageSize)||
                    StringUtils.isEmpty(token)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        try {

            result = orderService.querySaleServiceList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), token);

        }catch (Exception e){
            LOGGER.error("-------------查询售后列表 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
           
        }

        return result;
    }

    private final static String LOGIN_KEY = "login:*";

    @RequestMapping(value = "/key", method = RequestMethod.GET)
    public WebApiResponse<?> findredisKey(){

        Map<String, Object> map = new HashedMap();

//        Long size = stringRedisTemplate.opsForList().size("sn:order:20171121");

        for(String key : stringRedisTemplate.keys(LOGIN_KEY)){
//        for(String key : stringRedisTemplate.keys("sn:order:*")){

            if(map.size() > 9){
                break;
            }

            String memberInfo = null;
            try {
                memberInfo = stringRedisTemplate.opsForValue().get(key);
            }catch (Exception e){
                LOGGER.error("order/key "+e.toString());
            }
            if (memberInfo != null){
                map.put(key, memberInfo);
            }

        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

//        String key = CommonUtils.REIDS_ORDER_SN_KEY + ":" + sdf.format(new Date());

//        System.out.println("---------------------" + stringRedisTemplate.opsForList().rightPop(key));
//        EsGoodsRabbitDTO message = new EsGoodsRabbitDTO();

//        message.setGoodsUuid("97137901346757656");

        return WebApiResponse.success(map);
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

/*    @Autowired
    SnService snService;*/

/*    @Autowired
    private RabbitTemplate rabbitTemplate;*/
    
}
