package com.kingthy.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidMobile
{
    public static boolean isMobileNO(String mobiles)
    {
        
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p=Pattern.compile("^1[3,4,5,7,8]\\d{9}$");

        Matcher m = p.matcher(mobiles);
        
        return m.matches();
    }
    
    public static void main(String[] args)
    {
        System.out.println(isMobileNO("13388889999"));
    }
}
