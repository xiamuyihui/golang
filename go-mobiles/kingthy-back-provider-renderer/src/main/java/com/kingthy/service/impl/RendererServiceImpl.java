/**
 * 系统项目名称
 * com.kingthy.service.impl
 * RendererServiceImpl.java
 * 
 * 2017年5月18日-上午9:49:21
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang.StringUtils;
import org.csource.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.kingthy.service.RendererService;
import com.kingthy.websocket.ISocketConnection;
import com.kingthy.websocket.SocketClient;

/**
 *
 * 渲染器
 * 
 * 李克杰 2017年5月18日 上午9:49:21
 * 
 * @version 1.0.0
 *
 */
@Service("rendererService")
public class RendererServiceImpl implements RendererService
{
    private static final Logger logger = LoggerFactory.getLogger(RendererServiceImpl.class);
    
    // 线程池对象
    private static ExecutorService threadpool = Executors.newSingleThreadExecutor();
    
    @Override
    public String generate3Dgraphs(String memberUuid, long timespan, String fileId)
    {
        String tempDirectory = "";
        try
        {
            String key = memberUuid + timespan;
            // 程序路径
            String startupPath = RendererServiceImpl.class.getClassLoader().getResource("").getPath();
            // 临时文件保存目录
            tempDirectory = startupPath + "/temp/" + key + "/";
            String[] strs = fileId.split("/");
            if (strs.length <= 0)
            {
                return "";
            }
            String localFileName = strs[strs.length - 1];
            // 下载图片到本地
            downloadFile(tempDirectory, localFileName, fileId);
            // 获取用户的人体模型数据
            
            // 清空进度信息
            clearProgressMsg(tempDirectory);
            
            // 调用c++应用程序
            // tempDirectory是类的绝对路径，在c++中需要去掉‘/’
            String[] parms = {startupPath + "/dll/test.exe", fileId, tempDirectory.substring(1)};
            Process pcs = Runtime.getRuntime().exec(parms);
            
            // 发送进度到指定客户端
            sendProgressMsg(tempDirectory, key);
            
            int res = pcs.waitFor();
            String newfileId = "http://192.168.1.217/" + fileId;
            if (res == 0)
            {
                // 执行完成
                logger.info("文件转换成功，输出到" + tempDirectory);
                // 上传文件
                // newfileId = uploadFile(tempDirectory+localFileName, "xxxxxxx");
                logger.info("3D图文件id：" + newfileId);
            }
            
            return newfileId;
        }
        catch (IOException e)
        {
            deleteTempFile(tempDirectory);
            logger.error("RendererServiceImpl.generate3Dgraphs error:IOException", e);
        }
        catch (Exception e)
        {
            logger.error("RendererServiceImpl.generate3Dgraphs error:", e);
        }
        
        return null;
    }
    
    /**
     * 发送进度消息
     * 
     */
    private void sendProgressMsg(String tempDirectory, String key)
    {
        
        threadpool.execute(new Runnable()
        {
            
            @Override
            public void run()
            {
                int num =0;
                System.out.println(num);
                // 获取要发送的客户端
                ISocketConnection connection = SocketClient.get(key);
                if (connection == null)
                {
                    logger.info("RendererServiceImpl.sendProgressMsg", "客户端连接对象未找到，无法发送消息");
                    return;
                }
                while (true)
                {
                    // try
                    // {
                    // Thread.sleep(500);
                    // }
                    // catch (InterruptedException e)
                    // {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    num = getNum(StringUtils.trim(getGenerateProgress(key)));
                    if (num>0)
                    {
                        // 发送消息
                        connection.send(Integer.toString(num));
                    }
                    if (num==100)
                    {
                        // 删除临时文件
                        // deleteTempFile(tempDirectory);
                        break;
                    }
                    
                }
                
            }
        });
    }
    private int getNum(String str){
        int num=0;
        try{
            if(StringUtils.isBlank(str)){
                return 0;
            }else{
                num=Integer.parseInt(str);
            }
        }catch (Exception ex){

            logger.info("getNum error",ex.toString());
        }
        return num;
    }

    public void clearProgressMsg(String tempDirectory)
    {
        
        // progress.txt保存进度的文件，由渲染器写入渲染进度
        File file = new File(tempDirectory + "progress.txt");
        if (file.exists())
        {
            file.delete();
        }
    }
    
    /**
     * 
     * 获取3D模型文件生成进度
     */
    @Override
    public String getGenerateProgress(String key)
    
    {
        // 程序路径
        try
        {
            StringBuffer sBuffer = new StringBuffer();
            String startupPath = RendererServiceImpl.class.getClassLoader().getResource("").getPath();
            // 时间戳
            String tempDirectory = startupPath + "/temp/" + key + "/";
            // progress.txt保存进度的文件，由渲染器写入渲染进度
            File file = new File(tempDirectory + "progress.txt");
            if (file.exists())
            {
                FileReader fReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fReader);
                String str = "";
                while ((str = bufferedReader.readLine()) != null)
                {
                    sBuffer.append(System.lineSeparator() + str);
                }
                bufferedReader.close();
                fReader.close();
                
            }
            return sBuffer.toString();
        }
        catch (Exception e)
        {
            logger.error("RendererServiceImpl.getGenerateProgress error:", e);
        }
        
        return null;
    }
    
    /**
     * 
     * 删除临时文件
     */
    private void deleteTempFile(String tempDirectory)
    {
        File file = new File(tempDirectory);
        if (file.exists())
        {
            if (file.isDirectory())
            {
                File[] list = file.listFiles();
                for (int i = 0; i < list.length; i++)
                {
                    deleteFile(list[i]);
                }
            }
            file.delete();
        }
    }
    
    /**
     * 递归删除文件
     * 
     */
    private void deleteFile(File file)
    {
        if (file.isDirectory())
        {
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++)
            {
                this.deleteFile(list[i]);
            }
        }
        file.delete();
    }
    
    /**
     * 上传文件到服务器
     */
    private String uploadFile(String savePath, String fileName)
        throws IOException
    {
        
        File file = new File(savePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        byte[] fileByte = new byte[fileInputStream.available()];
        bufferedInputStream.read(fileByte);
        
        return FastDFSClient.uploadFile(fileByte, fileName);
        
    }
    
    /**
     * 从文件服务器下载文件
     */
    private void downloadFile(String savePath, String fileName, String fileId)
        throws IOException
    {
        // 下载文件
        byte[] fileByte = FastDFSClient.getFileByte(fileId);
        
        // 创建临时目录
        File dir = new File(savePath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        // 保存文件
        File file = new File(savePath + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(fileByte);
        
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        
    }
}
