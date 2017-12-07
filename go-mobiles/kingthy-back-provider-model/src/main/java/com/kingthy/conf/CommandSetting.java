package com.kingthy.conf;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：shell脚本配置（路径，超时等参数配置）
 * @author  likejie
 * @date 2017/10/10.
 */
@Component
@Data
public class CommandSetting
{


    /**计价服务脚本路径**/
    @Value("${command.model.path}")
    private  String modelCommandPath;

    /**计价服务脚本路径**/
    @Value("${command.bd.path}")
    private  String bdCommandPath;

    /**超时时间（秒）**/
    @Value("${timeout}")
    private  Long timeout;

    /**点云文件名**/
    @Value("${point.cloud.mls.name}")
    private  String pointCloudMlsName;

    /**点云文件名**/
    @Value("${joint.point.traned.name}")
    private  String jointPointTranedName;

    /**临时文件保存路径**/
    @Value("${modelfile.path}")
    private  String modelFilePath;

    /**标模路径*/
    @Value("${standard.model}")
    private  String standardModel;

    /**文件服务器ip*/
    @Value("${file.serverIp}")
    private String fileServerIp;

}
