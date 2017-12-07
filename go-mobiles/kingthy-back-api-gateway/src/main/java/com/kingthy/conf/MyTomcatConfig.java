package com.kingthy.conf;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 潘勇
 * @Data 2017/9/7 15:02.
 */
@Component()
public class MyTomcatConfig extends TomcatEmbeddedServletContainerFactory
{
    @Override
    public void customizeConnector(Connector connector)
    {
        super.customizeConnector(connector);
        Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
        protocol.setMaxConnections(6000);
        protocol.setMaxThreads(6000);
        protocol.setAcceptorThreadCount(6000);
        protocol.setMaxKeepAliveRequests(6000);
        protocol.setConnectionTimeout(6000);
    }
}
