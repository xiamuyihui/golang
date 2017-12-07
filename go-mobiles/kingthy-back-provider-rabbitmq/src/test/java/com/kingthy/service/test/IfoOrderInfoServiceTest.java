package com.kingthy.service.test;

import com.kingthy.KingthyProviderRabbitmqApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:40 on 2017/10/19.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderRabbitmqApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
//@Rollback
//@Transactional
public class IfoOrderInfoServiceTest {
}
