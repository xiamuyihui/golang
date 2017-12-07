package com.kingthy.util;


/**
 * 描述：TimeUtils
 * @author  赵生辉
 * @date 2017/10/23.
 */
public class TimeUtils
{
    public static long test(String str){
        long t=Integer.parseInt(str.substring(1,3))*60*60+Integer.parseInt(str.substring(4,6))*60+Integer.parseInt(str.substring(7,9));
        System.out.println(t);
        return t;
    }
}
