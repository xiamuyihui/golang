/**
 * 系统项目名称
 * com.kingthy.config
 * CorsConfig.java
 * 
 * 2017年5月23日-上午10:57:07
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 * 配置允许跨域请求
 * 
 * @author 李克杰 2017年5月23日 上午10:57:07
 * 
 * @version 1.0.0
 *
 */
@Configuration
public class CorsConfig
{
    private CorsConfiguration buildConfig()
    {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
    
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
