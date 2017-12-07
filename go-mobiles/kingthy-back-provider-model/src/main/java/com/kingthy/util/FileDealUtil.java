package com.kingthy.util;


import java.io.*;
import java.nio.file.Files;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name FileDealUtil
 * @description 文件处理工具类
 * @create 2017/10/16
 */
public class FileDealUtil
{
    /**
     *
     * 从指定文件路径读取文件byte
     **/
    public static byte[] getFileByteData(String filePath) throws IOException {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream bos = null;
        byte[] fileByte=null;
        try {
            File file = new File(filePath);
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
    /**
     *
     * 保存文件到指定目录
     */
    public static void saveFile(byte [] fileData,String fileSavePath,String fileName) throws IOException {

        BufferedOutputStream stream=null;
        FileOutputStream fileOutputStream=null;
        try {
            //创建目录
            File dir = new File(fileSavePath);
            if(!dir.exists()&&dir.isDirectory()){
                dir.mkdirs();
            }
            //创建文件
            File file = new File(fileSavePath+"\\"+fileName);
            fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(fileData);
        }catch (IOException ex){
            throw ex;
        }finally {
            if(stream!=null){
                stream.close();
            }
            if(fileOutputStream!=null){
                fileOutputStream.close();
            }
        }
    }
    /**文件拷贝**/
    public static void copy(String sourceDir,String dir,String fileName) throws IOException{
        try {
            File sourceFile = new File(sourceDir+"\\"+fileName);
            File toFileDir = new File(dir);
            if(!toFileDir.exists()){
                toFileDir.mkdirs();
            }
            File toFile=new File(dir+"\\"+fileName);
            Files.copy(sourceFile.toPath(), toFile.toPath());
        }catch (IOException e){
            throw  e;
        }
    }
    public static void deleteFile(String fileDir,String fileName){
        File file = new File(fileDir+"\\"+fileName);
        if(file.exists()){
            file.delete();
        }
    }
}
