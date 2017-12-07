package com.jxshop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.csource.FastDFSClient;

import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;
import net.coobird.thumbnailator.Thumbnailator;

public class Test
{
    static
    {
        // 不能漏掉这个，不然jmagick.jar的路径找不到
        System.setProperty("jmagick.systemclassloader", "no");
    }
    
    public static void main(String[] args)
        throws Exception
    {
        
        // File f = new File("C:/Users/Administrator/Desktop/sz4.jpg");
        // // File saveDir =
        // //
        // saveTempFile(request,file);http://192.168.1.217/group1/M00/00/15/wKgB21j9Y4eAGGNZAAO-56j0m8M264_120x120.jpg
        //
        // String fileId = FastDFSClient.uploadFile(f, "sz4.jpg");
        // System.out.println("http://192.168.1.217/" + fileId);
        // String masterFile = "http://192.168.1.217/" + fileId;
        // String s = uploadSlaveFile(fileId, "_20x40", "C:/Users/Administrator/Desktop/sz4.jpg");
        // FastDFSClient.downloadFile(s, "C:/Users/Administrator/Desktop/sz4-2040.jpg");
        // System.out.println("http://192.168.1.217/" + s);
//        InputStream is = FastDFSClient.downloadFile("group1/M00/00/15/wKgB21j9Y4eAGGNZAAO-56j0m8M264.jpg");
//
//        OutputStream os = new FileOutputStream(new File("C:/Users/Administrator/Desktop/asdf.jpg"));
//        // 小图
//        Thumbnailator.createThumbnail(is, os, 300, 400);
        
        // String s = uploadSlaveFile(fileId, "_20x40", "C:/Users/Administrator/Desktop/sz4.jpg");
        // 中图
        
        // 大图
        String orderInfo = "partner=2088222172464839&seller_id=2088222172464839&out_trade_no=20170922504388051&subject=外套o夏季外套&body=外套o夏季外套&total_amount=0.01&notify_url=http://www.jxkj.com&service=mobile.securitypay.pay&payment_type=1&_input_charset=utf-8&it_b_pay=2017-09-23 04:35:48&return_url=m.alipay.com&sign=dorARchAtyvndhA1tpilWu2tNxugAbED2KNsXSORaqKxrUR%2BdQGy696qxWds184henSD2QDJKTOoTlKENtHIF7r2vz3LB2pcVINGH2NOiodoAYTNP4726HJz1RXPTREHCw6%2BvID2YR4hSFUhi8FQkgJvLBKfOxlt5oXitU3F%2FZo%3D&sign_type=RSA";
//        String[] strs = orderInfo.split("&");
//        System.out.println("替换后："+strs);

        Type type2 = new TypeToken<HashMap<String,String>>(){}.getType();
        Gson gson = new Gson();
        Map map = gson.fromJson(orderInfo, type2);
        System.out.print(map.get("partner"));
        
    }
    
    public static void resetsize(String picFrom, String picTo)
    {
        try
        {
            ImageInfo info = new ImageInfo(picFrom);
            MagickImage image = new MagickImage(new ImageInfo(picFrom));
            MagickImage scaled = image.scaleImage(120, 97);
            scaled.setFileName(picTo);
            scaled.writeImage(info);
        }
        catch (MagickApiException ex)
        {
            ex.printStackTrace();
        }
        catch (MagickException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String uploadSlaveFile(String masterFileId, String prefixName, String slaveFilePath)
        throws Exception
    {
        String slaveFileId = "";
        String slaveFileExtName = "";
        if (slaveFilePath.contains("."))
        {
            slaveFileExtName = slaveFilePath.substring(slaveFilePath.lastIndexOf(".") + 1);
        }
        else
        {
            return slaveFileId;
        }
        
        // 上传文件
        try
        {
            slaveFileId = FastDFSClient.upload_file1(masterFileId, prefixName, slaveFilePath, slaveFileExtName, null);
        }
        catch (Exception e)
        {
        }
        
        return slaveFileId;
    }
}
