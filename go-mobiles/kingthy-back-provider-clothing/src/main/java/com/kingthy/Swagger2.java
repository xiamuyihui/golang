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

@Configuration
@EnableSwagger2
/**
 * 描述：Swagger2
 * @author  likejie
 * @date 2017/10/10.
 */
public class Swagger2
{
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kingthy.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("移动端后台支撑系统接口文档")
            //.description("测试地址:http://192.168.7.179:9527/swagger-ui.html")
            //.termsOfServiceUrl("http://192.168.7.179:9527/swagger-ui.html")
            .version("1.0")
            .build();
    }
}