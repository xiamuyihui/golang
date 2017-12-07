package com.kingthy.service.test;

import com.alibaba.fastjson.JSON;
import com.kingthy.KingthyProviderOrderApplication;
import com.kingthy.dto.*;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单元测试
 * @author xumin
 * @Description:
 * @DATE Created by 15:06 on 2017/5/15.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderOrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
@Rollback
@Transactional
//@SpringBootTest(classes = KingthyProviderOrderApplication.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {"feign.hystrix.enabled=false", "eureka.client.enabled=false", "feign.okhttp.enabled=false"})
//@SpringBootTest(classes = KingthyProviderOrderApplication.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWVfbmFtZSI6IjE4NjgyMzU4ODE1IiwiaXNzIjoicmVzdGFwaXVzZXIiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTQ5Njg5NjQ5OSwibmJmIjoxNDk2ODg5Mjk5fQ.iFgsaBexlboUg_mg_RxEepca52_uujVtqnnPtiHxaHY";

    @Test
    public void showSingleOrderInfo(){
        try {
            WebApiResponse<?> response = orderService.showSingleOrderInfo("2017050921107781");
            Assert.assertTrue("成功", response.getCode() == 0);

            if (response.getCode() == 0 || !StringUtils.isEmpty(response.getData())){
                SingleOrderDTO s = (SingleOrderDTO) response.getData();
                Assert.assertTrue("success", "97100764777807878".equals(s.getGoodsUuid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void createOrder(){

        String data = "\n" +
                "{\n" +
                "  \"address\": \"航盛科技大厦2楼\",\n" +
                "  \"amount\": 0.1,\n" +
                "  \"areaName\": \"北京市东城区\",\n" +
                "  \"areaUuid\": 2,\n" +
                "  \"consignee\": \"徐敏\",\n" +
                "  \"goods\": [\n" +
                "    {\n" +
                "      \"figureUuid\": \"test123\",\n" +
                "      \"goodsUuId\": \"97100764777807878\",\n" +
                "      \"invoiceTitle\": \"个人\",\n" +
                "      \"isInvoice\": true,\n" +
                "      \"memo\": \"测试啊测试啊\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"shippingMethodName\": \"顺丰\",\n" +
                "      \"shippingMethodUuid\": \"97071061572518093\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"paymentMethodType\": 0,\n" +
                "  \"paymentMethodUuid\": \"1\",\n" +
                "  \"phone\": \"13916445873\",\n" +
                "  \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWVfbmFtZSI6IjE4NjgyMzU4ODE1IiwiaXNzIjoicmVzdGFwaXVzZXIiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTQ5NDU3OTQyNCwibmJmIjoxNDk0NTcyMjIzfQ.peYyILazPly4h8LpzBh0s-95-DFcWpFwHQYBnVH0AOM\"\n" +
                "}";

        OrderDTO orderDTO = null;
        orderDTO = JSON.parseObject(data, OrderDTO.class);
        try {

            //创建订单成功
            WebApiResponse<?> response = orderService.createOrder(orderDTO);

            Assert.assertTrue("创建订单成功", response.getCode() == 0);

            //参数不正确
            orderDTO.setToken("");

            WebApiResponse<?> response1 = orderService.createOrder(orderDTO);

            Assert.assertTrue("参数不正确", response1.getCode() == 1);


            //查询不到商品
            orderDTO.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWVfbmFtZSI6IjE4NjgyMzU4ODE1IiwiaXNzIjoicmVzdGFwaXVzZXIiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTQ5NDU3OTQyNCwibmJmIjoxNDk0NTcyMjIzfQ.peYyILazPly4h8LpzBh0s-95-DFcWpFwHQYBnVH0AOM");

            orderDTO.getGoods().get(0).setGoodsUuId("9710076477780787812313");

            WebApiResponse<?> response2 = orderService.createOrder(orderDTO);

            Assert.assertTrue("查询不到商品", response2.getCode() == 1);

            //商品 ["+ dto.getGoodsName() + "] 已经删除
            orderDTO.getGoods().get(0).setGoodsUuId("97100764777808967");

            WebApiResponse<?> response3 = orderService.createOrder(orderDTO);

            Assert.assertTrue("商品已经删除", response3.getCode() == 1);

            //商品上架状态：0：未上架
            orderDTO.getGoods().get(0).setGoodsUuId("97100764777809189");

            WebApiResponse<?> response4 = orderService.createOrder(orderDTO);

            Assert.assertTrue("商品未上架", response4.getCode() == 1);

            //商品上架状态：2：延迟上架
            orderDTO.getGoods().get(0).setGoodsUuId("97100764777809226");

            WebApiResponse<?> response5 = orderService.createOrder(orderDTO);

            Assert.assertTrue("商品延迟上架", response5.getCode() == 1);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void delOrder(){

        String orderSn = "97100764777808193";
        try {

            //删除失败
            WebApiResponse<?> response = orderService.delOrder(orderSn, token);

            Assert.assertTrue("删除失败", response.getCode() == 1);

            //删除成功
            orderSn = "2017050921107561";
            WebApiResponse<?> response1 = orderService.delOrder(orderSn, token);

            Assert.assertTrue("删除成功", response1.getCode() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void updateOrderStatus(){
        OrderStatusReqDTO statusReqDTO = new OrderStatusReqDTO();
        statusReqDTO.setToken(token);

        try {

            //修改成功
            statusReqDTO.setOrderSn("2017050921107761");
            statusReqDTO.setStatus(2);//2:待发货
            WebApiResponse<?> response = orderService.updateOrderStatus(statusReqDTO);
            Assert.assertTrue("修改成功", response.getCode() == 0);

            //订单不存在
            statusReqDTO.setOrderSn("20170509211077615583");
            WebApiResponse<?> response1 = orderService.updateOrderStatus(statusReqDTO);
            Assert.assertTrue("订单不存在", response1.getCode() == 1);

            //订单状态不正确
            statusReqDTO.setOrderSn("2017050921107761");
            statusReqDTO.setStatus(null);
            WebApiResponse<?> response2 = orderService.updateOrderStatus(statusReqDTO);
            Assert.assertTrue("订单状态不正确", response2.getCode() == 1);

            //已付款的订单不能取消
            statusReqDTO.setOrderSn("2017050921107761");
            statusReqDTO.setStatus(5);//2:待发货
            WebApiResponse<?> response3 = orderService.updateOrderStatus(statusReqDTO);
            Assert.assertTrue("修改成功", response3.getCode() == 0);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());

        }
    }

    @Test
    public void comment(){
        OrderCommentDTO commentDTO = new OrderCommentDTO();
        try {

            commentDTO.setToken(token);
            commentDTO.setAnonymousFlag(false);
            commentDTO.setContent("test评论");
            commentDTO.setOrderSn("2017050921107761");
            WebApiResponse<?> response = orderService.comment(commentDTO);

            Assert.assertTrue("评论成功", response.getCode() == 0);

            //订单ID不存在
            commentDTO.setOrderSn("201705092110776112313");
            WebApiResponse<?> response1 = orderService.comment(commentDTO);

            Assert.assertTrue("订单ID不存在", response1.getCode() == 1);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void queryExchangeReason(){
        try{
            WebApiResponse<List<String>> response = (WebApiResponse<List<String>>) orderService.queryExchangeReason();

            Assert.assertTrue("查询成功", response.getCode() == 0);
            Assert.assertTrue("查询成功,有数据", response.getData().size() > 0);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void queryShipping(){
        try {

            WebApiResponse<List<ShippingInfoDTO>> response = (WebApiResponse<List<ShippingInfoDTO>>) orderService.queryShipping("2");

            Assert.assertTrue("查询成功", response.getCode() == 0);

            Assert.assertTrue("查询成功,有数据", response.getData().size() > 0);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void afterSaleService(){

        try{
            List<String> stringList = new ArrayList<>();
            stringList.add("http://192.168.1.217/group1/M00/00/18/wKgB2lkYoFGARyKNAAAAC4BX7vY382.jpg");

            AfterSaleServiceDTO dto = new AfterSaleServiceDTO();
            dto.setToken(token);
            dto.setApplyServiceType(0);
            dto.setExchangeReason("质量问题");
            dto.setMemo("测试啊测试");
            dto.setOrderSn("2017042521102341");
            dto.setListImg(stringList);
            WebApiResponse<?> response = orderService.afterSaleService(dto);

            Assert.assertTrue("售后服务不能重复申请", response.getCode() == 1);

            dto.setOrderSn("2017042521102181");

            WebApiResponse<?> response1 = orderService.afterSaleService(dto);

            Assert.assertTrue("申请售后服务成功", response1.getCode() == 0);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void updateShippingNumber(){

        try {
            SaleServiceShippingDTO dto = new SaleServiceShippingDTO();
            dto.setOrderSn("2017042521102341");
            dto.setShippingNumber("test123");
            dto.setToken(token);

//            WebApiResponse<?> response = orderService.updateShippingNumber(dto);

//            Assert.assertTrue("回填物流单号成功", response.getCode() == 0);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void cancelSaleService(){

        try {
            CancelServiceDTO dto = new CancelServiceDTO();
            dto.setToken(token);
            dto.setOrderSn("2017042521102341");
            WebApiResponse<?> response = orderService.cancelSaleService(dto);

            Assert.assertTrue("撤销售后成功", response.getCode() == 0);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void rabbitmqSend(){
        OrderCommentImgDTO dto = new OrderCommentImgDTO();
        dto.setBuyersUuid("133");
        dto.setMemberUuid("1213");
        rabbitTemplate.convertAndSend("topic.file.upload", "routing.file.buyers", dto);
    }

    @Test
    public void querySaleServiceList(){

        try {
            WebApiResponse<?> response = orderService.querySaleServiceList(Integer.valueOf(0), Integer.valueOf(10), token);

            Assert.assertTrue("查询售后列表成功", response.getCode() == 0);

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void queryServiceDetail(){

        try {
            String orderSn = "2017053121102221";

            WebApiResponse<?> response = orderService.queryServiceDetail(orderSn);

            Assert.assertTrue("查看售后进度成功", response.getCode() == 0);

            SaleServiceDetailDTO dto = (SaleServiceDetailDTO) response.getData();

            Assert.assertTrue("", "2017053121102221".equals(dto.getDetail().getOrderSn()));

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
}
