package com.kingthy.common;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

/**
 * 支付相關通用類
 *
 * Commons
 * 
 * 潘勇 2017年1月17日 下午4:57:37
 * 
 * @version 1.0.0
 *
 */
public class Commons
{
    
    /**
     * 解析URL参数转map parseParameters(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param map
     * @param data
     * @param encoding
     * @throws UnsupportedEncodingException void
     * @author pany
     * @exception @since 1.0.0
     */
    public static void parseParameters(Map<String, String> mapParmeter, String data, String encoding)
        throws UnsupportedEncodingException
    {
        String[] paramsArr = data.split("&");
        for (String param : paramsArr)
        {
            String[] temp = param.split("=");
            mapParmeter.put(temp[0], temp[1].replaceAll("\"", ""));
        }
    }
    
    /**
     * 生成随机码 generateUUID(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @return String
     * @author pany
     * @exception @since 1.0.0
     */
    public static String generateUUID()
    {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
            + str.substring(24);
        return temp;
    }
}
