package com.kingthy.conf;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 潘勇
 * @date 2017/11/22 10:22.
 */
@Configuration
@EnableAutoConfiguration
public class MyTomcatConfig {
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();

        AprLifecycleListener arpLifecycle = new AprLifecycleListener();
        tomcatFactory.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
        tomcatFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11Nio2Protocol handler = (Http11Nio2Protocol) connector.getProtocolHandler();
                /**最大并发数，worker线程数，处理io事件，默认200，线程数并非越大越好，如果maxThreads设置过大，那么cpu会
                 花费大量时间用于线程切换，整体效率会降低，具体根据硬件条件设置*/
                handler.setMaxThreads(200);
                /**最大连接数，nio默认10000 */
                handler.setMaxConnections(10000);
                /**用于接收连接的线程的数量，默认值是1。
                 * 一般这个指需要改动的时候是因为该服务器是一个多核CPU，如果是多核 CPU 一般配置为 2 */
                handler.setAcceptorThreadCount(2);
                /**接受一个连接后等待时间，默认60000 */
                handler.setConnectionTimeout(60000);
            }
        });
        return tomcatFactory;
    }
}
