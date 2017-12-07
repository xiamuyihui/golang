package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.kingthy.command.CommandManager;
import com.kingthy.command.CommandResult;
import com.kingthy.command.CommandStatus;
import com.kingthy.common.EncryptUtils;
import com.kingthy.conf.CommandSetting;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.controller.ModelController;
import com.kingthy.dto.UploadModelImageDTO;
import com.kingthy.dubbo.user.service.FigureDubboService;
import com.kingthy.entity.Figure;
import com.kingthy.entity.ModelImage;
import com.kingthy.exception.BizException;
import com.kingthy.request.CreaterModelReq;
import com.kingthy.response.CreaterModelRes;
import com.kingthy.response.EncryptUploadDTO;
import com.kingthy.service.FileUploadService;
import com.kingthy.service.ModelService;
import com.kingthy.util.FileDealUtil;
import com.kingthy.util.UnZip;
import org.csource.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shenghuizhao
 */
@Service("modelService")
public class ModelServiceImpl implements ModelService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);

    private static final int VERSION = 1;

    /**
     * 线程池对象
     */
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
        8,
        200, 3,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardPolicy());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommandManager commandManager;

    @Autowired
    private CommandSetting commandSetting;

    @Autowired
    private FileUploadService fileUploadService;

    @Reference(version = "1.0.0")
    private FigureDubboService figureDubboService;

    @Override
    public int sendImage(ModelImage modelImage)
    {
        //发送消息执行上传服务
        int result = 1;
        UploadModelImageDTO uploadModelImageDTO = new UploadModelImageDTO();
        uploadModelImageDTO.setFlankImage(modelImage.getFlankImage());
        uploadModelImageDTO.setFrontImage(modelImage.getFrontImage());
        uploadModelImageDTO.setModelUuid(modelImage.getModelUuid());
        uploadModelImageDTO.setToken(modelImage.getToken());
        uploadModelImageDTO.setCreateDate(new Date());
        rabbitTemplate.convertAndSend(RabbitmqConstants.MODEL_IMAGE_LISTENER_QUEUE, uploadModelImageDTO);
        return result;
    }

    @Override
    public CreaterModelRes createrModel(MultipartFile multipartFile, String memberUuid)
        throws IOException
    {
        String random = System.currentTimeMillis()+"";
        String modelFilePath = commandSetting.getModelFilePath();

        String pointCloudMlsName = commandSetting.getPointCloudMlsName();

        String jointPointTranedName = commandSetting.getJointPointTranedName();

        String zipFilePath = modelFilePath + multipartFile.getOriginalFilename();

        String fillPath = modelFilePath+memberUuid+random;

        try{
            //上传文件到本地
            multipartFile.transferTo(new File(zipFilePath));

        }catch (Exception e){
            LOGGER.error("ModelController uploadStream 方法" + e.getMessage());
            e.printStackTrace();
            throw new BizException("二进制流上传文件" + e);
        }

        File dir = new File(modelFilePath+memberUuid+random);
        if(!dir.exists()&&!dir.isDirectory()){
            dir.mkdirs();
        }

        //解压文件地制定路径
        UnZip.apache7ZDecomp(zipFilePath,modelFilePath+memberUuid+random);

        //获取解压文件，调用shell脚本生成模型
        CreaterModelReq createrModelReq = new CreaterModelReq();
        createrModelReq.setPointCloudMLSTxtPath(fillPath+"/"+pointCloudMlsName);
        createrModelReq.setJointPointTranedTxtPath(fillPath+"/"+jointPointTranedName);
        createrModelReq.setMemberUuid(memberUuid);
        createrModelReq.setModelPath(fillPath);

        //创建生成模型文件和cad文件并上传
        CreaterModelRes createrModelRes = null;
        try
        {
            createrModelRes = renderModel(createrModelReq);
        }
        catch (BizException bize)
        {
            throw bize;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(null == createrModelRes)
        {
            throw new BizException("创建模型失败");
        }
        Figure figure = new Figure();
        figure.setModelFile(JSON.toJSONString(createrModelRes));
        figure.setMemberUuid(memberUuid);
        createBodyModel(figure);

        return createrModelRes;
    }

    /**
     * 调用shell脚本生成模型文件 上传返回地址
     *
     * @param createrModelReq
     * @return
     */
    public CreaterModelRes renderModel(CreaterModelReq createrModelReq)
    {
        CreaterModelRes createrModelRes = new CreaterModelRes();
        //bd文件脚本路径
        String commandBd = commandSetting.getBdCommandPath();

        LOGGER.info("开始执行生成模型命令");
        /**1.生成模型文件***/
        String cmd = commandSetting.getModelCommandPath()+" "+createrModelReq.getPointCloudMLSTxtPath()+" "+createrModelReq.getJointPointTranedTxtPath()+" "+
            createrModelReq.getModelPath() + ".obj";
        LOGGER.info("命令：" + cmd);
        try
        {
            Process ps = Runtime.getRuntime().exec(cmd);
            int resultCode = ps.waitFor();

            if(resultCode == 0)
            {
                LOGGER.info("模型文件生成成功");
                //String cmdBd = commandBd+" "+commandSetting.getStandardModel()+" "+createrModelReq.getModelPath() + ".obj"+" "+createrModelReq.getModelPath() + ".bd";
                String cmdBd = commandBd+" "+commandSetting.getStandardModel()+" "+"/opt/cad/CadExe/2.obj"+" "+createrModelReq.getModelPath() + ".bd";
                LOGGER.info("开始执行生成bd命令");
                LOGGER.info("命令：" + cmd);
                Process psBd = Runtime.getRuntime().exec(cmdBd);
                int resultCodeBd = psBd.waitFor();

                if(resultCodeBd == 0)
                {
                    LOGGER.info("bd文件生成成功");
                    //2.上传模型文件到服务器
                    EncryptUploadDTO resultModelData = uploadFile(createrModelReq.getModelPath() + ".obj",createrModelReq.getMemberUuid()+".obj");
                    if (null != resultModelData && null != resultModelData.getFileUrl())
                    {
                        LOGGER.info("模型文件上传成功:" + resultModelData.getFileUrl());
                        //2.上传bd文件到服务器
                        EncryptUploadDTO resultBdData = uploadFile(createrModelReq.getModelPath() + ".bd",createrModelReq.getMemberUuid()+".bd");
                        if(null != resultBdData && null != resultBdData.getFileUrl())
                        {
                            createrModelRes.setObj(resultModelData.getFileUrl());
                            createrModelRes.setBd(resultModelData.getFileUrl());
                            return createrModelRes;
                        }
                    }
                }
                else
                {
                    /**读取脚本的错误流**/
                    String stderror=readStream(psBd.getErrorStream());
                    LOGGER.info("生成bd失败:" + stderror);
                    throw new BizException("生成bd失败:"+stderror);
                }
            }
            else
            {
                /**读取脚本的错误流**/
                String stderror=readStream(ps.getErrorStream());
                LOGGER.info("生成模型失败:" + stderror);
                throw new BizException("生成模型失败:"+stderror);
            }
        }
        catch (Exception e)
        {
            throw new BizException(e.getMessage());
        }
        return null;
    }

    //加密上传文件
    public EncryptUploadDTO uploadFile(String filePath,String fileName){
        byte[] fileByte = null;
        try
        {
            fileByte = FileDealUtil.getFileByteData(filePath);
            String key= EncryptUtils.getStringRandom(8);
            //文件加密
            File encryptFile = EncryptUtils.aes_encryptFile(fileByte, fileName,key);
            String fileId = FastDFSClient.uploadFile(encryptFile, fileName);
            EncryptUploadDTO dto=new EncryptUploadDTO();
            dto.setEncryptKey(key);
            dto.setFileUrl(commandSetting.getFileServerIp()+fileId);
            return dto;
        }
        catch (IOException ex)
        {
            LOGGER.error(ex.toString());
            throw new BizException("上传失败："+ex.toString());
        }

    }

    //创建人体模型
    public int createBodyModel(Figure figure)
    {
        Date date = new Date();
        figure.setCreateDate(date);
        figure.setModifyDate(date);
        figure.setCreator("creator");
        figure.setModifier("modifier");
        figure.setVersion(VERSION);
        figure.setDelFlag(false);
        figure.setIsDefault(false);
        int result = figureDubboService.insert(figure);
        if (result == 0)
        {
            throw new BizException("创建模型失败");
        }
        return result;
    }

    /**
     *
     * 读取流
     */
    public String readStream(InputStream inputStream) throws Exception{
        CommandResult commandResult = CommandResult.error(null);
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sbuffer = new StringBuffer();
        try {
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String line;
            while (commandResult.getStauts() != CommandStatus.STOP.getValue()) {
                if ((line = br.readLine()) != null) {
                    sbuffer.append(line);
                } else {
                    break;
                }
            }
        } catch (Exception ioe) {
            throw ioe;
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        return sbuffer.toString();
    }
}
