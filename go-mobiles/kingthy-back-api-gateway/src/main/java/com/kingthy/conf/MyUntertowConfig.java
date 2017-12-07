//package com.kingthy.conf;
//
//import io.undertow.UndertowOptions;
//import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
//import org.springframework.context.com.kingthy.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * @Author 潘勇
// * @Data 2017/11/13 19:44.
// */
//@Component()
//public class MyUntertowConfig
//{
//    @Bean
//    UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory()
//    {
//
//        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
//
//        // 这里也可以做其他配置
//        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
//
//        return factory;
//    }
//}
