package com.kingthy.controller;


import com.kingthy.common.CommonUtils;
import com.kingthy.common.EncryptUtils;
import com.kingthy.conf.FileUploadConfig;
import com.kingthy.dto.EncryptUploadDTO;
import com.kingthy.dto.UploadFileRequest;
import com.kingthy.dto.UploadFileResponse;
import com.kingthy.request.UploadModelmagReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.util.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



/**
 * 描述：有关服务消费端操作
 * @author  none
 * @date 2017/10/23.
 */
@RestController
@Api(value = "/", description = "有关服务消费端操作")
@RequestMapping("/fileUpload")
public class FileUploadController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    FileUploadConfig fileUploadConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "用户资料", notes = "完善个人资料上传图片")
    @PostMapping("/upload")
    public WebApiResponse<UploadFileResponse> upLoadFile(
        @RequestBody @ApiParam(name = "fileRequest", value = "图片上传对象", required = true) UploadFileRequest fileRequest)
    {

        try
        {
            byte[] bs = Base64.decode(fileRequest.getFileData());
            if (null == bs)
            {
                return WebApiResponse.error("无法解析客户端上传的文件");
            }
            String fileId = FastDFSClient.uploadFile(bs, fileRequest.getFileName());
            
            if (StringUtils.isNotBlank(fileId))
            {
                // 判断是否生成缩略图
                String fileUrl = fileUploadConfig.getFileServerIp()+"/" + fileId;
                
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileRequest.getFileName());
                uploadFileResponse.setFileUrl(fileUrl);

                return WebApiResponse.success(uploadFileResponse);
            }
            else
            {
                return WebApiResponse.error("无法连接服务器，上传图片返回null");
            }
            
        }
        catch (Exception e)
        {
            LOGGER.error("ReWorkController upLoadFile 方法" + e.getMessage());
            return WebApiResponse.error("图片上传失败" + e);
        }
    }
    
    @ApiOperation(value = "上传Append类型文件，支持文件续传", notes = "")
    @PostMapping("/uploadAppendFile")
    public WebApiResponse<UploadFileResponse> uploadAppendFile(
        @RequestBody @ApiParam(name = "fileRequest", value = "上传对象", required = true) UploadFileRequest fileRequest)
    {
        try
        {
            
            byte[] bs = Base64.decode(fileRequest.getFileData());
            if (null == bs)
            {
                return WebApiResponse.error("无法解析客户端上传的文件");
            }
            String fileId = FastDFSClient.upload_append_file(bs, fileRequest.getFileName());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileId(fileId);
            return WebApiResponse.success(uploadFileResponse);
        }
        catch (Exception e)
        {
            LOGGER.error("FileUploadController upLoadFile 方法" + e.getMessage());
            return WebApiResponse.error("文件上传失败" + e);
        }
    }
    
    @ApiOperation(value = "获取已上传文件大小", notes = "")
    @PostMapping("/getFileSize")
    public WebApiResponse<?> getFileSize(
        @RequestBody @ApiParam(name = "fileRequest", value = "上传对象", required = true) UploadFileRequest fileRequest)
    {
        try
        {
            String fileId = fileRequest.getFileId();
            if (StringUtils.isBlank(fileId))
            {
                return WebApiResponse.error("参数fileId不能为空");
            }
            long size = FastDFSClient.getFileSize(fileId);
            if (size == -1)
            {
                return WebApiResponse.error("文件不存在");
            }
            return WebApiResponse.success(size);
        }
        catch (Exception e)
        {
            LOGGER.error("FileUploadController upLoadFile 方法" + e.getMessage());
            return WebApiResponse.error("文件上传失败" + e);
        }
    }
    
    @ApiOperation(value = "追加Append类型文件，支持文件续传", notes = "")
    @PostMapping("/appendFile")
    public WebApiResponse<?> appendFile(
        @RequestBody @ApiParam(name = "fileRequest", value = "上传对象", required = true) UploadFileRequest fileRequest)
    {
        try
        {
            
            byte[] bs = Base64.decode(fileRequest.getFileData());
            String fileId = fileRequest.getFileId();
            Integer totalSize = fileRequest.getTotalSize();
            if (null == bs)
            {
                return WebApiResponse.error("无法解析客户端上传的文件");
            }
            if (StringUtils.isBlank(fileId))
            {
                return WebApiResponse.error("参数fileId不能为空");
            }
            if (totalSize == null || totalSize == 0)
            {
                return WebApiResponse.error("参数totalSize无效");
            }
            
            int res = FastDFSClient.append_file(fileId, bs, fileRequest.getTotalSize());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileId(fileId);
            uploadFileResponse.setFileName(fileRequest.getFileName());
            
            if (res == 1)
            {
                // 部分文件上传成功，不能访问
                return WebApiResponse.success("文件追加成功，但是文件未全部上传，无法访问，请继续上传");
            }
            // 全部子文件上传成功，只有当文件全部上传完成，才返回完整到url
            String fileUrl = fileUploadConfig.getFileServerIp()+"/" + fileId;
            uploadFileResponse.setFileUrl(fileUrl);
            return WebApiResponse.success(uploadFileResponse);
        }
        catch (Exception e)
        {
            LOGGER.error("FileUploadController upLoadFile 方法" + e.getMessage());
            return WebApiResponse.error("文件追加失败" + e);
        }
    }

    /**
     * 二进制流上传文件
     * @param files
     * @return
     */
    @RequestMapping(value = "/stream", method = RequestMethod.POST)
    public WebApiResponse<?> uploadStream(@RequestParam("file") @ApiParam(name = "file", value = "上传对象", required = true)  MultipartFile[] files){

        List<String> list = new ArrayList<>();

        try{

            uploadMultipartFiles(files, list);

        }catch (Exception e){
            LOGGER.error("FileUploadController uploadStream 方法" + e.getMessage());
            e.printStackTrace();
            return WebApiResponse.error("二进制流上传文件" + e);
        }

        return WebApiResponse.success(list);
    }


    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public WebApiResponse<?> uploadVideoStream(@RequestParam("file") @ApiParam(name = "file", value = "上传对象", required = true)  MultipartFile files)
        throws IOException
    {

        List<String> list = new ArrayList<>();
        MultipartFile[] multipartFile = {files};
        try{

            uploadMultipartFiles(multipartFile, list);

        }catch (Exception e){
            LOGGER.error("FileUploadController uploadStream 方法" + e.getMessage());
            e.printStackTrace();
            return WebApiResponse.error("二进制流上传文件" + e);
        }

        String videoPath = list.get(0);
        UUID uuid = UUID.randomUUID();
        String imagePath = "/data/service/image/"+uuid.toString()+".jpg";
        saveVideoThumbnail(videoPath,imagePath);
        File file = new File(imagePath);
        Long nowTime = System.currentTimeMillis();
        while( !file.isFile()){
            file = new File(imagePath);
            if(System.currentTimeMillis()-nowTime>=5000)
            {
                break;
            }
        }
        String fileId = FastDFSClient.uploadFile(file, uuid.toString()+".jpg");
        String fileUrl = fileUploadConfig.getFileServerIp()+"/"+ fileId;
        list.add(fileUrl);
        file.delete();
        return WebApiResponse.success(list);
    }

    @RequestMapping(value = "/videoup", method = RequestMethod.POST)
    public WebApiResponse<?> uploadVideoStream(@RequestBody String files)
        throws IOException
    {
        String videoPath = files;
        UUID uuid = UUID.randomUUID();
        String imagePath = "/data/service/image/"+uuid.toString()+".jpg";
        saveVideoThumbnail(videoPath,imagePath);
        File file = new File(imagePath);
        Long nowTime = System.currentTimeMillis();
        while( !file.isFile()){
            file = new File(imagePath);
            if(System.currentTimeMillis()-nowTime>=5000)
            {
                break;
            }
        }
        String fileId = FastDFSClient.uploadFile(file, uuid.toString()+".jpg");
        String fileUrl = fileUploadConfig.getFileServerIp()+"/" + fileId;
        file.delete();
        return WebApiResponse.success(fileUrl);

    }

    /*public static void main(String[] args)
    {
     File file  = new File("C:\\Users\\KingThy\\Pictures\\89.jpg");
     if(file.isFile())
     {
     file.delete();
     }

        //String fileId = FastDFSClient.uploadFile(file, "896.jpg");
        System.out.println(0);

    }*/


    /**
     * 二进制流上传文件-返回原始文件名
     * @param files
     * @return
     */
    @RequestMapping(value = "/stream/files", method = RequestMethod.POST)
    public WebApiResponse<?> uploadStreamFiles(@RequestParam("file") @ApiParam(name = "file", value = "上传对象", required = true)  MultipartFile[] files){

        List<String> list = new ArrayList<>();

        try{

            uploadMultipartFiles(files, list, true);

        }catch (Exception e){
            LOGGER.error("FileUploadController uploadStreamFiles 方法" + e.getMessage());
            e.printStackTrace();
            return WebApiResponse.error("二进制流上传文件-返回原始文件名" + e);
        }

        return WebApiResponse.success(list);
    }

    private void uploadMultipartFiles(MultipartFile[] files, List<String> list) throws IOException{
        uploadMultipartFiles(files, list, false);
    }

    /**
     *
     * @param files
     * @param list
     * @param fileName 是否返回原始文件名
     * @throws IOException
     */
    private void uploadMultipartFiles(MultipartFile[] files, List<String> list, boolean fileName) throws IOException {

        for (MultipartFile multipartFile : files){

            String fileUrl = null;

            String key = CommonUtils.UPLOAD_FILE_REDIS_KEY + ":" + DigestUtils.md5Hex(multipartFile.getInputStream());

            fileUrl = stringRedisTemplate.opsForValue().get(key);

            if (StringUtils.isBlank(fileUrl)){

                String fileId = FastDFSClient.uploadFile(multipartFile.getBytes(), multipartFile.getOriginalFilename());

                fileUrl = fileUploadConfig.getFileServerIp()+"/" + fileId;

                stringRedisTemplate.opsForValue().set(key, fileUrl);

            }

            if (!StringUtils.isBlank(fileUrl)){

                fileUrl = fileName ? fileUrl + "?filename=" + multipartFile.getOriginalFilename() : fileUrl;

                list.add(fileUrl);
            }
        }
    }

    @RequestMapping(value = "/uploadModelImage", method = RequestMethod.POST)
    public WebApiResponse<?> uploadModelImage(@RequestParam("frontImage") MultipartFile frontImage,@RequestParam("flankImage") MultipartFile flankImage){

        UploadModelmagReq uploadModelmagReq = new UploadModelmagReq();
        try{

            String frontfileId = FastDFSClient.uploadFile(frontImage.getBytes(), frontImage.getOriginalFilename());

            String frontfileUrl =fileUploadConfig.getFileServerIp()+"/" + frontfileId;

            String flankFileId = FastDFSClient.uploadFile(flankImage.getBytes(), flankImage.getOriginalFilename());

            String flankFileUrl = fileUploadConfig.getFileServerIp()+"/" + flankFileId;

            uploadModelmagReq.setFrontImage(frontfileUrl);
            uploadModelmagReq.setFlankImage(flankFileUrl);

        }catch (Exception e){
            LOGGER.error("FileUploadController uploadModelImage 方法" + e.getMessage());
            e.printStackTrace();
            return WebApiResponse.error("上传文件失败" + e);
        }

        return WebApiResponse.success(uploadModelmagReq);
    }

    @ApiOperation(value = "以二进制格式，上传文件", notes = "以二进制格式，上传文件")
    @PostMapping("/uploadFileByBinary")
    public WebApiResponse<?> uploadFileByBinary(@RequestBody UploadFileRequest req) {
        try {
            if(req.getFileByte().length<=0){
                return WebApiResponse.error("fileData error:"+WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }
            String fileId= FastDFSClient.uploadFile(req.getFileByte(),req.getFileName());
            return WebApiResponse.success(fileUploadConfig.getFileServerIp()+"/"+fileId);
        } catch (Exception ex) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }
    @ApiOperation(value = "加密（ASE）上传文件,form 表单提交方式", notes = "加密（ASE）上传文件")
    @RequestMapping(value = "/encryptUploadFile", method = RequestMethod.POST)
    public WebApiResponse<?> encryptUploadFile(@RequestParam("file") @ApiParam("二进制流格式") MultipartFile file) {
        try {
            byte[] fileData = file.getBytes();
            //加密key
            String key=EncryptUtils.getStringRandom(8);
            //文件加密
            File encryptFile = EncryptUtils.aes_encryptFile(fileData, file.getOriginalFilename(),key);
            String fileId = FastDFSClient.uploadFile(encryptFile, file.getOriginalFilename());
            EncryptUploadDTO dto=new EncryptUploadDTO();
            dto.setEncryptKey(key);
            dto.setFileUrl(fileUploadConfig.getFileServerIp()+"/" + fileId);
            return WebApiResponse.success(dto);
        } catch (Exception e) {
            LOGGER.error("FileUploadController encryptUploadFile 方法异常：" + e.getMessage());
            return WebApiResponse.error("上传文件失败" + e);
        }
    }


    public static void saveVideoThumbnail(String originFileUri,String imageSavePath) throws IOException {
        //ffmpeg -i xxx.mp4 -y -f image2 -t 0.001 -s 125x125 xxx.jpg
        List<String> cmd = new java.util.ArrayList<String>();
        cmd.add("/usr/local/bin/ffmpeg");
        cmd.add("-i");
        cmd.add(originFileUri);
        cmd.add("-y");
        cmd.add("-f");
        cmd.add("image2");
        cmd.add("-ss");
        cmd.add("2");
        cmd.add("-t");
        cmd.add("0.001");
        cmd.add("-s");
        cmd.add("200*150");
        cmd.add(imageSavePath);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmd);
        builder.start();
    }
    
}
