package com.kingthy.common;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:27 on 2017/8/24.
 * @Modified by:
 */
public class CommonUtils
{
    
    public static final String updaterId = "MALL";
    
    public static final String officially_desinger = "10000";
    
    public static final String REQUEST_HEADER_PARAMETER = "Authorization";

    public static final String REIDS_ORDER_SN_KEY = "sn:order";

    public static final String UPLOAD_FILE_REDIS_KEY = "upload:file";

    public static String uuidToSharding(String uuid)
    {
        int i;
        if (Integer.MIN_VALUE != uuid.hashCode())
        {
            i = Math.abs(uuid.hashCode()) % 5;
        }
        else
        {
            i = Integer.MIN_VALUE % 5;
        }
        
        String sharding = null;
        
        switch (i)
        {
            case 0:
                sharding = CommonUtils.ShardingEnum.A.name();
                break;
            
            case 1:
                sharding = CommonUtils.ShardingEnum.B.name();
                break;
            
            case 2:
                sharding = CommonUtils.ShardingEnum.C.name();
                break;
            
            case 3:
                sharding = CommonUtils.ShardingEnum.D.name();
                break;
            case 4:
                sharding = CommonUtils.ShardingEnum.E.name();
                break;
        }
        
        return sharding;
    }
    
    public enum ShardingEnum
    {
        A, B, C, D, E
    }

    public static void main(String [] agrs){
        System.out.println("--------------------" + uuidToSharding("97397638320965673"));
    }
    
}
