package com.kingthy.controller;


import com.kingthy.common.EncryptUtils;
import com.kingthy.conf.FileUploadConfig;
import com.kingthy.dto.EncryptUploadDTO;
import com.kingthy.request.DownloadFileReq;
import com.kingthy.response.WebApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.csource.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.utils.io.ByteToInputStream;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;


/**
 * 描述：DownloadController
 * @author  赵生辉
 * @date 2017/10/23.
 */
@RestController
@Api(value = "/", description = "下载文件模块")
@RequestMapping("/fileDownload")
public class FileDownloadController
{
    private static final Logger LOG = LoggerFactory.getLogger(FileDownloadController.class);

    @ApiOperation(value = "下载文件", notes = "下载文件")
    @PostMapping("/downloadFile")
    public  WebApiResponse downloadFile(@RequestBody DownloadFileReq req) {

        try {
            if(StringUtils.isBlank(req.getFileId())&&StringUtils.isBlank(req.getFileurl())){
                return WebApiResponse.error("文件地址错误！");
            }
            if(req.getIsDecrypt()){
                if(StringUtils.isBlank(req.getEncryptKey())){
                    return WebApiResponse.error("文件解密需要秘钥EncryptKey");
                }
            }
            //从文件服务器下载文件
            InputStream inputStream = FastDFSClient.downloadFile(req.getFileId());
            if(inputStream==null){
                return WebApiResponse.error("文件查找失败，无法下载文件！");
            }
            byte[] fileData;
            if(req.getIsDecrypt()) {
                String fileName= UUID.randomUUID().toString();
                //解密文件
                File file = EncryptUtils.aes_decryptFile(inputStream, fileName, req.getEncryptKey());
                //获取文件二进制数据
                fileData = getFileByteData(file);
            }else{
                fileData=ByteToInputStream.input2byte(inputStream);
            }
            return WebApiResponse.success(fileData);
        } catch (Exception ex) {
            LOG.error("downAndDecryptFile error " + ex.toString());
            return WebApiResponse.error("文件下载失败" + ex.toString());
        }
    }



    @ApiOperation(value = "下载并解密文件,只做测试", notes = "解密下载文件")
    @PostMapping("/decryptTest")
    public WebApiResponse<?> decryptTest(@RequestBody EncryptUploadDTO dto)

            throws MalformedURLException
    {

        URL url = new URL(dto.getFileUrl());
        try
        {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();

            File file= EncryptUtils.aes_decryptFile(inStream,"test",dto.getEncryptKey());

            InputStream fileSaveInputStream=new FileInputStream(file);

            File saveFile=new File("E:\\tempfile\\test.jpg");

            OutputStream outputStream=new FileOutputStream(saveFile);

            byte[] bs=new byte[1024];
            while(fileSaveInputStream.read(bs)!=-1){
                outputStream.write(bs);
            }
            inStream.close();
            fileSaveInputStream.close();
            outputStream.close();
            return WebApiResponse.success();
        }
        catch (IOException e)
        {
            LOG.error("FileDownloadController downLoad 方法" + e.getMessage());
            return WebApiResponse.error("文件下载失败" + e);
        }
    }

    /**
     *
     * 从指定文件路径读取文件byte
     **/
    private static byte[] getFileByteData(File file) throws IOException {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream bos = null;
        byte[] fileByte=null;
        try {
            fileInputStream = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            fileByte = bos.toByteArray();
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return fileByte;
    }
}
