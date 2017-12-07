/**
 * 系统项目名称
 * com.kingthy
 * FileUploadControllerTest1.java
 * 
 * 2017年5月15日-下午3:54:30
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.kingthy.controller.FileUploadController;
import com.kingthy.dto.UploadFileRequest;
import com.kingthy.response.WebApiResponse;

/**
 *
 * FileUploadControllerTest1
 * 
 * 李克杰 2017年5月15日 下午3:54:30
 * 
 * @version 1.0.0
 *
 */
public class FileUploadControllerTest
{
    FileUploadController controller;
    
    @Before
    public void SetUp()
    {
        controller = new FileUploadController();
    }
    
    @Test
    public void getFileSize()
    {
        try
        {
            /*WebApiResponse<?> response = null;
            
            UploadFileRequest fileRequest = new UploadFileRequest();
            response = controller.getFileSize(fileRequest);
            assertEquals(response.getMessage(), "参数fileId不能为空");
            
            fileRequest.setFileId("222");
            response = controller.getFileSize(fileRequest);
            assertEquals(response.getMessage(), "文件不存在");

            fileRequest.setFileId("group1/M00/00/16/wKgB21kY5cKEIRdbAAAAAPAl9-0755.txt");
            response = controller.getFileSize(fileRequest);*/
            
        }
        catch (Exception e)
        {
            fail();
            System.out.println(e);
        }
        
    }
    
    @Test
    public void uploadAppendFile()
    {
        try
        {
            UploadFileRequest fileRequest = new UploadFileRequest();
            WebApiResponse<?> response = controller.uploadAppendFile(fileRequest);
            assertEquals(response.getCode(), 1);
            
            fileRequest.setFileData("233545");
            response = controller.uploadAppendFile(fileRequest);
            assertEquals(response.getCode(), 1);
            
            fileRequest.setFileData("c3NkZnNkZmRmc2Rmc2RmZHNmc2Q=");
            response = controller.uploadAppendFile(fileRequest);
            assertEquals(response.getCode(), 0);
        }
        catch (Exception e)
        {
            fail();
            System.out.println(e);
        }
        
    }
    
    @Test
    public void appendFile()
    {
        try
        {
            UploadFileRequest fileRequest = new UploadFileRequest();
            
            WebApiResponse<?> response = controller.appendFile(fileRequest);
            System.out.println(response);
            
            fileRequest.setFileData("RkZGRkZGRkZGztK1vbXYt721xMqxtPq1xLeiyfq1xLe9yr23osn6oaOho6GioaKju6O7QEAjIw==");
            response = controller.appendFile(fileRequest);
            assertEquals(response.getCode(), 1);
            
            fileRequest.setFileData("RkZGRkZGRkZGztK1vbXYt721xMqxtPq1xLeiyfq1xLe9yr23osn6oaOho6GioaKju6O7QEAjIw==");
            fileRequest.setFileId("group1/M00/00/16/wKgB21kY9LCEavmKAAAAAPAl9-02075993");
            response = controller.appendFile(fileRequest);
            assertEquals(response.getCode(), 1);
            
            fileRequest.setFileData("RkZGRkZGRkZGztK1vbXYt721xMqxtPq1xLeiyfq1xLe9yr23osn6oaOho6GioaKju6O7QEAjIw==");
            fileRequest.setFileId("group1/M00/00/16/wKgB21kY9LCEavmKAAAAAPAl9-02075993");
            fileRequest.setTotalSize(75);
            response = controller.appendFile(fileRequest);
            assertEquals(response.getCode(), 0);
        }
        catch (Exception e)
        {
            fail();
            System.out.println(e);
        }
        
    }
}
