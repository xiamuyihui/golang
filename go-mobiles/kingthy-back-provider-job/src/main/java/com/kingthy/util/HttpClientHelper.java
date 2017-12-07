package com.kingthy.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientHelper
 * <p>
 * @author yuanml 2017/9/15
 *
 * @version 1.0.0
 */
public class HttpClientHelper
{
    /**
     * @Description:使用HttpURLConnection发送post请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:26:07
     */
    public static String sendPost(String urlParam, Map<String, Object> params, String charset)
    {
        StringBuffer resultBuffer = null;
        // // 构建请求参数
         StringBuffer sbParams = getStringBuffer(params);
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        // 发送请求
        try
        {
            URL url = new URL(urlParam);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
             con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
             if (sbParams != null && sbParams.length() > 0)
             {
                osw = new OutputStreamWriter(con.getOutputStream(), charset);
                osw.write(sbParams.substring(0, sbParams.length() - 1));
                osw.flush();
             }

            // 读取返回内容
            resultBuffer = new StringBuffer();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null)
            {
                resultBuffer.append(temp);
            }
//             }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            finallyClose(con, br, null, osw);
        }
        
        return resultBuffer.toString();
    }
    
    /**
     * @Description:发送get请求保存下载文件
     * @author:liuyc
     * @time:2016年5月17日 下午3:27:29
     */
    public static void sendGetAndSaveFile(String urlParam, Map<String, Object> params, String fileSavePath)
    {
        StringBuffer sbParams = getStringBuffer(params);
        
        HttpURLConnection con = null;
        BufferedReader br = null;
        FileOutputStream os = null;
        try
        {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0)
            {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            }
            else
            {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            InputStream is = con.getInputStream();
            os = new FileOutputStream(fileSavePath);
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = is.read(buf)) != -1)
            {
                os.write(buf, 0, count);
            }
            os.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            finallyClose(con, br, os, null);
        }
    }
    
    private static StringBuffer getStringBuffer(Map<String, Object> params)
    {
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0)
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        return sbParams;
    }
    
    private static void finallyClose(HttpURLConnection con, BufferedReader br, FileOutputStream os,
        OutputStreamWriter osw)
    {
        try
        {
            if (br != null){
                br.close();
            }
            if (os != null){
                os.close();
            }
            if (osw != null){

                osw.close();
            }
        }
        catch (IOException e)
        {
            if (br != null){
                br = null;
            }
            if (os != null){
                os = null;
            }
            if (osw != null){
                osw = null;
            }
            throw new RuntimeException(e);
        }
        finally
        {
            if (con != null)
            {
                con.disconnect();
                con = null;
            }
        }
    }
    
    /**
     * @Description:使用HttpURLConnection发送get请求
     * @author:liuyc
     * @time:2016年5月17日 下午3:27:29
     */
    public static String sendGet(String urlParam, Map<String, Object> params, String charset)
    {
        StringBuffer resultBuffer = null;
        // 构建请求参数
        StringBuffer sbParams = getStringBuffer(params);
        HttpURLConnection con = null;
        BufferedReader br = null;
        try
        {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0)
            {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            }
            else
            {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null)
            {
                resultBuffer.append(temp);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            finallyClose(con, br, null, null);
        }
        return resultBuffer.toString();
    }
    
    /**
     * @Description:读取一行数据，contentLe内容长度为0时，读取响应头信息，不为0时读正文
     * @time:2016年5月17日 下午6:11:07
     */
    private static String readLine(InputStream is, int contentLength, String charset)
        throws IOException
    {
        List<Byte> lineByte = new ArrayList<Byte>();
        byte tempByte;
        int cumsum = 0;
        if (contentLength != 0)
        {
            do
            {
                tempByte = (byte)is.read();
                lineByte.add(Byte.valueOf(tempByte));
                cumsum++;
            }
            // cumsum等于contentLength表示已读完
            while (cumsum < contentLength);
        }
        else
        {
            do
            {
                tempByte = (byte)is.read();
                lineByte.add(Byte.valueOf(tempByte));
            }
            // 换行符的ascii码值为10
            while (tempByte != 10);
        }
        
        byte[] resutlBytes = new byte[lineByte.size()];
        for (int i = 0; i < lineByte.size(); i++)
        {
            resutlBytes[i] = (lineByte.get(i)).byteValue();
        }
        return new String(resutlBytes, charset);
    }

}
