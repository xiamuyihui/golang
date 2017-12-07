package com.kingthy.conf;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author 潘勇
 * @Data 2017/6/20 10:22.
 */
@Component
public class MyTomcatConfig extends TomcatEmbeddedServletContainerFactory
{
    protected void customizeConnector(Connector connector)
    {
        super.customizeConnector(connector);
        Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
        //protocol.setMaxConnections(50000);
        protocol.setMaxThreads(2000);
    }
}
