package com.kingthy.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 描述：shell脚本配置（路径，超时等参数配置）
 * @author  likejie
 * @date 2017/10/10.
 */
@Component
public class CommandSetting {


    /**计价服务脚本路径**/
    @Value("${command.price.path}")
    private  String priceCommandPath;

    /**走秀视频脚本路径**/
    @Value("${command.video.path}")
    private  String videoCommandPath;

    /**超时时间（秒）**/
    @Value("${timeout}")
    private  Long timeout;

    /**临时文件保存路径**/
    @Value("${tempfile.path}")
    private  String tempFilePath;


    /**临时文件保存路径**/
    @Value("${modelfile.path}")
    private  String modelFilePath;


    public String getPriceCommandPath() {
        return priceCommandPath;
    }

    public Long getTimeout() {
        return timeout;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public String getVideoCommandPath() {
        return videoCommandPath;
    }

    public String getModelFilePath() {
        return modelFilePath;
    }
}
