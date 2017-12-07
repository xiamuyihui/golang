package com.kingthy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * User:pany <br>
 * <br>
 * Time:11:10 <br>
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket newsApi()
    {
        return new Docket(DocumentationType.SWAGGER_2).groupName("consumer")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kingthy.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("系统API接口管理")
            .description("各个微服务")
            .contact("潘勇")
            .license("Apache License Version 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0")
            .build();
    }
}