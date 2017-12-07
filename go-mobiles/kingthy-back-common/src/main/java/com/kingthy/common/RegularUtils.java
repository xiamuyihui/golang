package com.kingthy.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：正则
 *
 * @author likejie
 * @date 2017/11/6
 */
public class RegularUtils {

    /**纯数字*/
    public static final  String NUMBER="^[0-9]*$";
    /**手机号码*/
    public static final  String PHONE="^1[3,4,5,7,8]\\d{9}$";


    public static final Pattern NUMBER_PATTERN= Pattern.compile(NUMBER);
    public static final Pattern PHONE_PATTERN= Pattern.compile(PHONE);

    private  static boolean check(Pattern pattern,String val){
        Matcher matcher=pattern.matcher(val);
        return matcher.matches();
    }
    /**
     * 是否是数字
     * @param val
     * @return boolean
     */
    public static boolean isNumber(String val){
        return check(NUMBER_PATTERN ,val);
    }
    /**
     * 是否是手机号码
     * @param val
     * @return boolean
     */
    public static boolean isPhone(String val){
        return check(PHONE_PATTERN ,val);
    }

}
