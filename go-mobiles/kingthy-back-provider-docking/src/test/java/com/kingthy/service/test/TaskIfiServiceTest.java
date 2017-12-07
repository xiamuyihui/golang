package com.kingthy.service.test;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.kingthy.KingthyProviderDockingApplication;
import com.kingthy.service.TaskIfiService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author xumin
 * @Description:单元测试
 * @DATE Created by 16:44 on 2017/9/26.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderDockingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
@Rollback
@Transactional
//@SpringBootTest(classes = KingthyProviderOrderApplication.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {"feign.hystrix.enabled=false", "eureka.client.enabled=false", "feign.okhttp.enabled=false"})
//@SpringBootTest(classes = KingthyProviderOrderApplication.class)
public class TaskIfiServiceTest {

    @Autowired
    private TaskIfiService taskIfiService;

    @Test
    public void showSingleOrderInfo(){
        try {


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}
