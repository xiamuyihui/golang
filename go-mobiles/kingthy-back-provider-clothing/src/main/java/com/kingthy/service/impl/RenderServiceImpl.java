package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.command.CommandManager;
import com.kingthy.command.CommandResult;
import com.kingthy.command.CommandStatus;
import com.kingthy.conf.CommandSetting;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.RenderVideoDTO;
import com.kingthy.dto.UploadFileRequest;
import com.kingthy.dubbo.user.service.ShowVideosDubboService;
import com.kingthy.entity.ShowVideos;
import com.kingthy.remote.FileUploadService;
import com.kingthy.request.ModelRenderReq;
import com.kingthy.request.ShowVideosReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.RenderService;
import com.kingthy.util.FileDealUtil;
import com.kingthy.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.util.resources.cldr.ar.CalendarData_ar_TN;


import java.io.*;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.zip.ZipOutputStream;


/**
 * 描述：渲染业务逻辑处理接口
 * @author  likejie
 * @date 2017/10/10.
 */
@Service
public class RenderServiceImpl implements RenderService {

    private static final Logger logger= LoggerFactory.getLogger(RenderServiceImpl.class);

    /**线程池对象*/
    private static final ExecutorService THREAD_POOL =  new ThreadPoolExecutor(
            8,
            200, 3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(3),new ThreadPoolExecutor.DiscardPolicy());

    @Autowired
    private CommandManager commandManager;

    @Autowired
    private CommandSetting commandSetting;

    @Autowired
    private FileUploadService fileUploadService;

    @Reference(version = "1.0.0")
    private ShowVideosDubboService showVideosDubboService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    /**模型渲染**/
    public WebApiResponse<?> modelRender(ModelRenderReq req){

        try {

            String fileId=UUID.randomUUID().toString();
            if(StringUtils.hasText(req.getExtName())){
                fileId+=req.getExtName();
            }
            //临时文件存储路径
            String tempFilePath = commandSetting.getTempFilePath();
            //模型文件存储路径
            String modelFilePath = commandSetting.getModelFilePath();
            //保存文件到临时文件夹
            FileDealUtil.saveFile(req.getFilelData(), tempFilePath,fileId);
            if(req.isUnzip()) {
                //解压缩文件
                ZipUtil.readByApacheZipFile(tempFilePath + "\\" + fileId, modelFilePath);
            }else {
                //文件拷贝
                FileDealUtil.copy(tempFilePath ,modelFilePath ,fileId);
            }
            //删除临时文件
            FileDealUtil.deleteFile(tempFilePath,fileId);
            //调用脚本渲染
            /**通过线程异步执行***/

            /**threadpool.execute(() -> renderVideo(req));**/
            return WebApiResponse.success();
        }catch (Exception ex){
            logger.error(ex.toString());
            return WebApiResponse.error(ex.toString());
        }
    }
    /**
     *
     * 调用shell脚本渲染视频
     **/
    private  void renderVideo(ShowVideosReq req) {


        //脚本路径
        String command = commandSetting.getVideoCommandPath();
        //临时文件存储路径，存储生成的文件到临时文件夹
        String tempFilePath = commandSetting.getTempFilePath();
        //参数
        String[] parms = {command, "1 2 3 4 5 6"};

        logger.info("开始执行命令");


        /**1.生成走秀视频***/
        CommandResult result = commandManager.stExecCommand(THREAD_POOL, parms);
        /**推送的消息*/
        RenderVideoDTO message = new RenderVideoDTO();
        if (result.getStauts() == CommandStatus.SUCCESS.getValue()) {
            logger.info("走秀视频生成成功");

            /**2.上传视频文件到服务器***/
            byte[] fileByte=null;
            try {
                fileByte = FileDealUtil.getFileByteData("E:\\tempfile\\test.jpg");
            }catch (IOException ex){
                logger.error(ex.toString());
                return;
            }
            UploadFileRequest dto = new UploadFileRequest();
            dto.setFileByte(fileByte);
            dto.setFileName("test.jpg");
            WebApiResponse<String> response = fileUploadService.uploadFileByBinary(dto);

            /**3.保存视频文件地址到数据库***/
            if (response.getCode() == WebApiResponse.SUCCESS_CODE) {

                ShowVideos showVideos = new ShowVideos();
                showVideos.setVideoUrl(response.getData());
                //视频名称
                showVideos.setName("");
                showVideos.setDelFlag(false);
                showVideos.setMemberUuid(req.getMemberUuid());
                Date now = new Date();
                showVideos.setCreateDate(now);
                showVideos.setModifyDate(now);
                int res = showVideosDubboService.insert(showVideos);

            } else {
                logger.error("视频文件上传失败！");
            }
            //生成成功
            message.setMessage("视频生成成功！");
            message.setUrl(response.getData());
        } else {
            //生成失败
            message.setMessage("视频生成失败！");
            message.setUrl("");
        }

        /**4.发送消息通知客户端***/

    }
    @Override
    public WebApiResponse<?> createVideo(ShowVideosReq req) {

        /**通过线程异步执行***/
        THREAD_POOL.execute(() -> renderVideo(req));
        return null;
    }
    @Override
    public void sendMq() {
        RenderVideoDTO dto=new RenderVideoDTO();
        dto.setUrl("测试");
        rabbitTemplate.convertAndSend(RabbitmqConstants.RENDER_VIDEO_EXCHANGE,RabbitmqConstants.RENDER_VIDEO_ROUTING,dto);
    }

}
