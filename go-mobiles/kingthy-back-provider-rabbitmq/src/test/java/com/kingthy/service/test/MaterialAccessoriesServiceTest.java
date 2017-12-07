package com.kingthy.service.test;

import com.kingthy.KingthyProviderRabbitmqApplication;
import com.kingthy.dto.MaterialAccessoriesDTO;
import com.kingthy.service.MaterialAccessoriesService;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:07 on 2017/9/25.
 * @Modified by:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderRabbitmqApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
//@Rollback
//@Transactional
public class MaterialAccessoriesServiceTest
{
    @Autowired
    private MaterialAccessoriesService materialAccessoriesService;

    @Test
    public void syncCIPPInfoTest(){

        MaterialAccessoriesDTO message = new MaterialAccessoriesDTO();
        message.setTableType(1);
        message.setOperSt(0);
        message.setCode("97391580821848177");
        try {
            int result = materialAccessoriesService.syncCIPPInfo(message);
            Assert.assertTrue("success", result > 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
