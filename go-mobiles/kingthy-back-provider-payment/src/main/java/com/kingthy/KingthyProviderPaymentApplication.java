package com.kingthy;


import com.kingthy.unionpay.sdk.SdkConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 */
@SpringCloudApplication
@EnableEurekaClient
public class KingthyProviderPaymentApplication
{
    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(KingthyProviderPaymentApplication.class, args);
        //初始化银联支付配置参数
        SdkConfig.getConfig().loadPropertiesFromSrc();
        String key = "os.name";
        String windows = "windows";
        if(System.getProperty(key).toLowerCase().indexOf(windows)!=-1){
            String path=KingthyProviderPaymentApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            SdkConfig.getConfig().setMiddleCertPath(path+"/certs_test/acp_test_middle.cer");
            SdkConfig.getConfig().setSignCertPath(path+"/certs_test/acp_test_sign.pfx");
            SdkConfig.getConfig().setRootCertPath(path+"/certs_test/acp_test_root.cer");
            SdkConfig.getConfig().setEncryptCertPath(path+"/certs_test/acp_test_enc.cer");
        }


    }
}
