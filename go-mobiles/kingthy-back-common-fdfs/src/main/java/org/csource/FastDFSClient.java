package org.csource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * 
 *
 * FastDFSClient
 * 
 * @author pany 2016年4月14日 下午4:30:27
 * 
 * @version 1.0.0
 *
 */
public class FastDFSClient
{
    
    // private static final String CONF_FILENAME =
    // Thread.currentThread().getContextClassLoader().getResource("").getPath() + "fdfs_client.conf";
    private static final String CONF_FILENAME = "fdfs/fdfs_client.conf";
    
    private static StorageClient1 storageClient1 = null;
    
    private static Logger logger = Logger.getLogger(FastDFSClient.class.getName());
    
    /**
     * 只加载一次.
     */
    static
    {
        try
        {
            logger.info("=== CONF_FILENAME:" + CONF_FILENAME);
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null)
            {
                logger.log(Level.INFO, "getConnection return null");
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer, "group1");
            if (storageServer == null)
            {
                logger.log(Level.INFO, "getConnection return null");
            }
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        }
        catch (Exception e)
        {
            logger.log(Level.INFO, "init exception", e);
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param file 文件
     * @param fileName 文件名
     * @return 返回Null则为失败
     */
    public static String uploadFile(File file, String fileName)
    {
        FileInputStream fis = null;
        try
        {
            NameValuePair[] metaList = null; // new NameValuePair[0];
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null)
            {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            
            String fileid = storageClient1.upload_file1(file_buff, getFileExt(fileName), metaList);
            return fileid;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile exception", ex);
            return null;
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    logger.log(Level.INFO, "uploadFile IOException", e);
                }
            }
        }
    }
    
    /**
     * 
     * @param 文件字节数组 文件
     * @param fileName 文件名
     * @return 返回Null则为失败
     */
    public static String uploadFile(byte[] fileByte, String fileName)
    {
        FileInputStream fis = null;
        try
        {
            NameValuePair[] metaList = null; // new NameValuePair[0];
            
            String fileid = storageClient1.upload_file1(fileByte, getFileExt(fileName), metaList);
            return fileid;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile Exception", ex);
            return null;
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    logger.log(Level.INFO, "uploadFile IOException", e);
                }
            }
        }
    }
    
    /**
     * 根据组名和远程文件名来删除一个文件
     * 
     * @param groupName 例如 "group1" 如果不指定该值，默认为group1
     * @param fileName 例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
     * @return 0为成功，非0为失败，具体为错误代码
     */
    public static int deleteFile(String groupName, String fileName)
    {
        try
        {
            int result = storageClient1.delete_file(groupName == null ? "group1" : groupName, fileName);
            return result;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile Exception", ex);
            return 0;
        }
    }
    
    /**
     * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
     * 
     * @param fileId file_id源码中的解释file_id the file id(including group name and filename);例如
     *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
     * @return 0为成功，非0为失败，具体为错误代码
     */
    public static int deleteFile(String fileId)
    {
        try
        {
            int result = storageClient1.delete_file1(fileId);
            return result;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile deleteFile", ex);
            return 0;
        }
    }
    
    /**
     * 修改一个已经存在的文件
     * 
     * @param oldFileId 原来旧文件的fileId, file_id源码中的解释file_id the file id(including group name and filename);例如
     *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
     * @param file 新文件
     * @param filePath 新文件路径
     * @return 返回空则为失败
     */
    public static String modifyFile(String oldFileId, File file, String filePath)
    {
        String fileid = null;
        try
        {
            // 先上传
            fileid = uploadFile(file, filePath);
            if (fileid == null)
            {
                return null;
            }
            // 再删除
            int delResult = deleteFile(oldFileId);
            if (delResult != 0)
            {
                return null;
            }
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile modifyFile", ex);
            return null;
        }
        return fileid;
    }
    
    /**
     * 文件下载
     * 
     * @param fileId
     * @return 返回一个数组
     */
    public static byte[] getFileByte(String fileId)
    {
        try
        {
            byte[] bytes = storageClient1.download_file1(fileId);
            return bytes;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile downloadFile", ex);
            return null;
        }
    }
    
    /**
     * 文件下载
     * 
     * @param fileId
     * @return 返回一个流
     */
    public static InputStream downloadFile(String fileId)
    {
        try
        {
            byte[] bytes = storageClient1.download_file1(fileId);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile downloadFile", ex);
            return null;
        }
    }
    
    /**
     * 获取文件后缀名（不带点）.
     * 
     * @return 如："jpg" or "".
     */
    private static String getFileExt(String fileName)
    {
        if (StringUtils.isBlank(fileName) || !fileName.contains("."))
        {
            return "";
        }
        else
        {
            return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
        }
    }
    
    public static String upload_file1(String masterFileId, String prefixName, String slaveFilePath,
        String slaveFileExtName, Object object)
    {
        // TODO Auto-generated method stub
        FileInputStream fis = null;
        try
        {
            NameValuePair[] metaList = null; // new NameValuePair[0];
            
            String fileid =
                storageClient1.upload_file1(masterFileId, prefixName, slaveFilePath, slaveFileExtName, null);
            return fileid;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile Exception", ex);
            return null;
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    logger.log(Level.INFO, "uploadFile IOException", e);
                }
            }
        }
    }
    
    public static int downloadFile(String file_id, String local_filename)
    {
        // TODO Auto-generated method stub
        try
        {
            int result = storageClient1.download_file1(file_id, local_filename);
            return result;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "uploadFile downloadFile", ex);
        }
        return 0;
    }
    
    /**
     * 
     * 上传支持断点续传文件
     */
    public static String upload_append_file(byte[] file_buff, String fileName)
    {
        try
        {
            NameValuePair[] meta_list = null; // new NameValuePair[0];
            String fileId = storageClient1.upload_appender_file1(file_buff, getFileExt(fileName), meta_list);
            return fileId;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "append_file1", ex);
            return null;
        }
        
    }
    
    /**
     * 获取文件大小
     */
    public static long getFileSize(String file_id)
    {
        
        try
        {
            FileInfo fileInfo = storageClient1.get_file_info1(file_id);
            long fileSize = fileInfo.getFileSize();
            return fileSize;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "getFileSize", ex);
            return -1;
        }
    }
    
    /**
     * 
     * 追加文件（必须是upload_append_file形式上传的文件）
     */
    public static int append_file(String file_id, byte[] file_buff, int offset)
    {
        try
        {
            FileInfo fileInfo = storageClient1.get_file_info1(file_id);
            if (fileInfo == null)
            {
                // 文件不存在
                return 1;
            }
            // 已上传文件大小
            int fileSize = (int)fileInfo.getFileSize();
            // 本次上传的文件大小
            int file_buff_length = file_buff.length;
            int res = -1;
            if (offset == fileSize)
            {
                // 追加文件内容
                res = storageClient1.append_file1(file_id, file_buff);
            }
            else if (fileSize - offset == file_buff_length)
            {
                // 从指定断点重复上传
                res = storageClient1.append_file1(file_id, file_buff, offset, file_buff_length);
            }
            
            return res;
        }
        catch (Exception ex)
        {
            logger.log(Level.INFO, "append_file1", ex);
            return 1;
        }
        
    }
}
