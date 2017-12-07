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
 * 描述：SwaggerConfig
 * @author  none
 * @date 2017/10/23.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket newsApi()
    {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kingthy.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("系统API接口管理")
            .description("各个微服务")
            .version("1.0")
            .build();
    }
}