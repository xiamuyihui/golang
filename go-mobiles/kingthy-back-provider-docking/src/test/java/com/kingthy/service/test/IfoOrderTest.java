package com.kingthy.service.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.kingthy.KingthyProviderDockingApplication;
import com.kingthy.dto.IfoOrderDetailBomDTO;
import com.kingthy.dto.IfoOrderItemDTO;
import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.dto.IfoStitchingStyleDTO;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.service.IfoOrderDetailBomService;
import com.kingthy.service.IfoOrderInfoService;
import com.kingthy.service.IfoOrderStyleFileService;
import com.kingthy.service.IfoStitchingStyleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:14 on 2017/11/10.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderDockingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
//@Rollback
//@Transactional
//@SpringBootTest(classes = KingthyProviderOrderApplication.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {"feign.hystrix.enabled=false", "eureka.client.enabled=false", "feign.okhttp.enabled=false"})
//@SpringBootTest(classes = KingthyProviderOrderApplication.class)
public class IfoOrderTest {

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    @Autowired
    private IfoOrderDetailBomService ifoOrderDetailBomService;

    @Autowired
    private IfoOrderStyleFileService ifoOrderStyleFileService;

    @Autowired
    private IfoStitchingStyleService ifoStitchingStyleService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference(version = "1.0.0", timeout = 3000)
    private SnDubboService snDubboService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IfoOrderTest.class);

    private void createOrderTest(){

        String order = "{\n" +
                "\"filePath\": [\n" +
                "                \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AWy6_AABd0zhXaQc334.png\",\n" +
                "                \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6ABpdoAABd0zhXaQc165.png\",\n" +
                "                \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AZCQUAABd0zhXaQc871.png\",\n" +
                "                \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AS1FpAABd0zhXaQc743.png\"\n" +
                "            ],\n" +
                "            \"orderItemSn\": \"20170922504382551\",\n" +
                "            \"price\": 100,\n" +
                "            \"stCodes\": \"test\",\n" +
                "            \"stFlag\": false,\n" +
                "            \"styleCode\": \"2017050221232176\",\n" +
                "            \"styleName\": \"test\",\n" +
                "            \"styleTypeCode\": \"KS0057\",\n" +
                "            \"styleTypeName\": \"\",\n" +
                "            \"syFilePath\": \"e://\",\n" +
                "            \"token\": \"wxhtesttoken\",\n" +
                "            \"userUuid\": \"wxhtestuserUuid\"\n" +
                "}";

        Gson gson = new Gson();


        String bom = "{\n" +
                "              \"bomInfoList\": [\n" +
                "                {\n" +
                "                    \"componentTypeCode\": \"BJ0051\",\n" +
                "                    \"componentTypeName\": \"大身\",\n" +
                "                    \"factFlag\": false,\n" +
                "                    \"materialCode\": \"M20171108510\",\n" +
                "                    \"materialName\": \"T丝绸\",\n" +
                "                    \"materialTypeCode\": \"M\",\n" +
                "                    \"partsCode\": \"BJ0051\",\n" +
                "                    \"partsName\": \"大身\",\n" +
                "                    \"partsPath\": \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AYwdiAABd0zhXaQc163.png\",\n" +
                "                    \"partsPatternPath\": \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AbpYvAABd0zhXaQc210.png\",\n" +
                "                    \"piecesCode\": \"1590\",\n" +
                "                    \"piecesName\": \"1590\",\n" +
                "                    \"piecesPath\": \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6ACcQ_AAA2GoGe3AU077.png\",\n" +
                "                    \"quantity\": 1,\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"unit\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"componentTypeCode\": \"BJ0051\",\n" +
                "                    \"componentTypeName\": \"大身\",\n" +
                "                    \"factFlag\": false,\n" +
                "                    \"materialCode\": \"F2017111019\",\n" +
                "                    \"materialName\": \"T树脂扣\",\n" +
                "                    \"materialTypeCode\": \"F\",\n" +
                "                    \"partsCode\": \"BJ0051\",\n" +
                "                    \"partsName\": \"大身\",\n" +
                "                    \"partsPath\": \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6ALW75AABd0zhXaQc605.png\",\n" +
                "                    \"partsPatternPath\": \"http://192.168.1.217/group1/M00/00/3B/wKgB21oE5r6AXmDAAABd0zhXaQc840.png\",\n" +
                "                    \"piecesCode\": \"1641\",\n" +
                "                    \"piecesName\": \"1641\",\n" +
                "                    \"piecesPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AbUSVAAA_3n1jvD4119.png\",\n" +
                "                    \"quantity\": 1,\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"unit\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"componentTypeCode\": \"BJ0051\",\n" +
                "                    \"componentTypeName\": \"大身\",\n" +
                "                    \"factFlag\": false,\n" +
                "                    \"materialCode\": \"F20171109419\",\n" +
                "                    \"materialName\": \"树脂扣\",\n" +
                "                    \"materialTypeCode\": \"F\",\n" +
                "                    \"partsCode\": \"BJ0051\",\n" +
                "                    \"partsName\": \"大身\",\n" +
                "                    \"partsPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AT-25AABd0zhXaQc108.png\",\n" +
                "                    \"partsPatternPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6ABseyAABd0zhXaQc371.png\",\n" +
                "                    \"piecesCode\": \"1714\",\n" +
                "                    \"piecesName\": \"1714\",\n" +
                "                    \"piecesPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AJkGaAAA35hfi4VA804.png\",\n" +
                "                    \"quantity\": 1,\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"unit\": 1\n" +
                "                }\n" +
                "            ],\n" +
                "            \"orderItemSn\": \"20170922504382551\",\n" +
                "            \"token\": \"wxhtesttoken\",\n" +
                "            \"userUuid\": \"wxhtestuserUuid\"\n" +
                "}";

        String stitching = "{\n" +
                "             \"orderItemSn\": \"20170922504382551\",\n" +
                "            \"stitchingInfoList\": [\n" +
                "                {\n" +
                "                    \"edgeLen\": 54.28591537475586,\n" +
                "                    \"fuliaoCode\": \"test\",\n" +
                "                    \"fuliaoQuantity\": 10,\n" +
                "                    \"piecesCodes\": \"1590,1641\",\n" +
                "                    \"standardCodes\": \"0\",\n" +
                "                    \"stitchingCode\": \"1801\",\n" +
                "                    \"stitchingDesc\": \"P1片E1边和P2片E1边采用来去缝\",\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"wireCode\": \"test\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"edgeLen\": 10.406553268432617,\n" +
                "                    \"fuliaoCode\": \"test\",\n" +
                "                    \"fuliaoQuantity\": 10,\n" +
                "                    \"piecesCodes\": \"1590,1641\",\n" +
                "                    \"standardCodes\": \"0\",\n" +
                "                    \"stitchingCode\": \"1834\",\n" +
                "                    \"stitchingDesc\": \"P1片E2边和P2片E2边采用来去缝\",\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"wireCode\": \"test\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"edgeLen\": 68.89143371582031,\n" +
                "                    \"fuliaoCode\": \"test\",\n" +
                "                    \"fuliaoQuantity\": 10,\n" +
                "                    \"piecesCodes\": \"1590,1714\",\n" +
                "                    \"standardCodes\": \"0\",\n" +
                "                    \"stitchingCode\": \"1863\",\n" +
                "                    \"stitchingDesc\": \"P1片E3边和P3片E1边采用来去缝\",\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"wireCode\": \"test\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"edgeLen\": 54.55208206176758,\n" +
                "                    \"fuliaoCode\": \"test\",\n" +
                "                    \"fuliaoQuantity\": 10,\n" +
                "                    \"piecesCodes\": \"1641,1714\",\n" +
                "                    \"standardCodes\": \"0\",\n" +
                "                    \"stitchingCode\": \"1898\",\n" +
                "                    \"stitchingDesc\": \"P2片E3边和P3片E2边采用来去缝\",\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"wireCode\": \"test\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"edgeLen\": 11.590749740600586,\n" +
                "                    \"fuliaoCode\": \"test\",\n" +
                "                    \"fuliaoQuantity\": 10,\n" +
                "                    \"piecesCodes\": \"1641,1714\",\n" +
                "                    \"standardCodes\": \"0\",\n" +
                "                    \"stitchingCode\": \"1927\",\n" +
                "                    \"stitchingDesc\": \"P2片E4边和P3片E3边采用来去缝\",\n" +
                "                    \"styleCode\": \"2017050221232176\",\n" +
                "                    \"wireCode\": \"test\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"token\": \"wxhtesttoken\",\n" +
                "            \"userUuid\": \"wxhtestuserUuid\"\n" +
                "}";

        String style = "{\n" +
                "    \"orderItemSn\": \"20170922504382551\",\n" +
                "    \"styleInfoList\": [\n" +
                "        {\n" +
                "            \"acreage\": 22723.89453125,\n" +
                "            \"breadth\": 75.7463150024414,\n" +
                "            \"materialCode\": \"M20171108510\",\n" +
                "            \"pltPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AStc3AAJbVH3TuVs966.plt\",\n" +
                "            \"quantity\": 3,\n" +
                "            \"styleCode\": \"2017050221232176\",\n" +
                "            \"unit\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"acreage\": 22723.89453125,\n" +
                "            \"breadth\": 75.7463150024414,\n" +
                "            \"materialCode\": \"F2017111019\",\n" +
                "            \"pltPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AStc3AAJbVH3TuVs966.plt\",\n" +
                "            \"quantity\": 3,\n" +
                "            \"styleCode\": \"2017050221232176\",\n" +
                "            \"unit\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"acreage\": 22723.89453125,\n" +
                "            \"breadth\": 75.7463150024414,\n" +
                "            \"materialCode\": \"F20171109419\",\n" +
                "            \"pltPath\": \"http://192.168.1.217/group1/M00/00/3C/wKgB21oE5r6AStc3AAJbVH3TuVs966.plt\",\n" +
                "            \"quantity\": 3,\n" +
                "            \"styleCode\": \"2017050221232176\",\n" +
                "            \"unit\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"token\": \"wxhtesttoken\",\n" +
                "    \"userUuid\": \"wxhtestuserUuid\"\n" +
                "}";

        try {

//            String styleCode = snDubboService.generateSn("opus").getData();
            String styleCode = ifoOrderInfoService.getSn("opus");

            String orderItemSn = ifoOrderInfoService.getSn("order");

            LOGGER.error("-------------orderItemSn------------" + orderItemSn);

            if (StringUtils.isEmpty(styleCode)){
                Assert.fail("fail");
                return;
            }

            IfoOrderItemDTO ifoOrderDTO = gson.fromJson(order, IfoOrderItemDTO.class);
            ifoOrderDTO.setStyleCode(styleCode);
            ifoOrderDTO.setOrderItemSn(orderItemSn);
            ifoOrderInfoService.createIfoOrder(ifoOrderDTO);

            IfoOrderDetailBomDTO detailBomDTO = gson.fromJson(bom, IfoOrderDetailBomDTO.class);
            detailBomDTO.getBomInfoList().forEach(c -> c.setStyleCode(styleCode));
            detailBomDTO.setOrderItemSn(orderItemSn);
            ifoOrderDetailBomService.createOrderItemBom(detailBomDTO);

            IfoStitchingStyleDTO ifoStitchingStyleDTO = gson.fromJson(stitching, IfoStitchingStyleDTO.class);
            ifoStitchingStyleDTO.setOrderItemSn(orderItemSn);
            ifoStitchingStyleService.createStitchingStyle(ifoStitchingStyleDTO);

            IfoOrderStyleFileDTO ifoOrderStyleFileDTO = gson.fromJson(style, IfoOrderStyleFileDTO.class);
            ifoOrderStyleFileDTO.getStyleInfoList().forEach(g -> g.setStyleCode(styleCode));
            ifoOrderStyleFileDTO.setOrderItemSn(orderItemSn);
            ifoOrderStyleFileService.createOrderStyleFile(ifoOrderStyleFileDTO);

            Assert.assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    private long getExpire(String key){
        if (!StringUtils.isEmpty(key)) {
            return stringRedisTemplate.getExpire(key);
        }
        return 0;
    }

    @Test
    public void batchCreateOrderTest(){
//        stringRedisTemplate.opsForValue().set("addServletMapping","addServletMapping",5, TimeUnit.DAYS);

//        long s = stringRedisTemplate.getExpire("addServletMapping");

        for (int i = 0; i<1; i++){
            createOrderTest();
//            System.out.println("--------------------"+s);
        }
    }
}
