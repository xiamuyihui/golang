package com.kingthy.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 描述：MyConfig
 * @author  none
 * @date 2017/10/23.
 */
@Configuration
public class MyConfig
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurerAdapter()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/fileUpload/**");
            }
            
            /**
             * 处理url链接中出现xx.com?a=x.y.z 导致无法获取 z 的问题
             */
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer)
            {
                configurer.setUseSuffixPatternMatch(false);
            }
        };
    }
}
