package com.kingthy.util;

/**
 * Created by soap on 16/7/4. 配置信息
 */
public class ConfigUtil
{
    
    /**
     * 测试数据类型
     */
    public static final class UPLOAD_TYPE
    {
        /**
         * upload file
         **/
        public static final  String UPLOAD_IMAGE_TYPE = "image";
        
        /**
         * upload uri
         */
        public static final  String UPLOAD_URI_TYPE = "url";
    }
    
    /**
     * api 调用地址
     */
    public static final  class NET_WORK
    {
        
        public static final  String API_URI = "http://api.open.tuputech.com/v3/recognition/";
        
    }
    
    /**
     * 文件限制
     */
    
    public static final class FILE_LIMIT
    {
        /**
         * 图片最大 800KB 左右
         */
        public static final int MAX_IMAGE_LENGTH = 800 * 1024;
        
        /**
         * 每个请求最大图片数量
         */
        public static final int MAX_IMAGE_LIST_SIZE = 200;
        
    }
    
    /**
     * 公用Key文件名
     */
    public static final String PUBLIC_TUPU_KEY_PATH = "open_tuputech_com_public_key.pem";
    
}
