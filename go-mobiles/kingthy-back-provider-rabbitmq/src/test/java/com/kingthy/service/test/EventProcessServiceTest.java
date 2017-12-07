package com.kingthy.service.test;

import com.google.gson.Gson;
import com.kingthy.KingthyProviderRabbitmqApplication;
import com.kingthy.request.EventPublishReq;
import com.kingthy.service.EventProcessService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:51 on 2017/11/28.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderRabbitmqApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
//@Rollback
//@Transactional
public class EventProcessServiceTest
{
    @Autowired
    private EventProcessService eventProcessService;

    @Test
    public void insertTest(){

        EventPublishReq message = new EventPublishReq();

        try {

            String json = "{\"memberUuid\":\"97137901347464904\",\"goodsAndQuantityList\":[{\"goodsUuid\":\"97100764777807939\",\"quantity\":1}]}";

            Gson gson = new Gson();

            message = gson.fromJson(json, EventPublishReq.class);

            int result = eventProcessService.insert(message);

            Assert.assertTrue("success", result > 0);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
