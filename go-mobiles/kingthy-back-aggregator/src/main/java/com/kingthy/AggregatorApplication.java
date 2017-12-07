package com.kingthy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
/**
 * 打开面板 ：http://localhost:9506/hystrix
 * 输入：http://localhost:9506/turbine.stream
 * @author  likejie
 */
@SpringCloudApplication
@EnableTurbine
@EnableHystrixDashboard
public class AggregatorApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }
}
