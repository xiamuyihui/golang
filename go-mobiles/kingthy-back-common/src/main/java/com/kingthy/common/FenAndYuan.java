package com.kingthy.common;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 金额转换工具类
 * <p>
 * FenAndYuan
 * <p>
 * 潘勇 2017年1月17日 下午4:53:30
 *
 * @version 1.0.0
 */
public class FenAndYuan
{

    /**
     * 分转换为元.
     *
     * @param fen 分
     * @return 元
     * @throws Exception
     */
    public static String fromFenToYuan(final String fen)
        throws Exception
    {
        String yuan = "";
        final int MULTIPLIER = 100;
        Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
        Matcher matcher = pattern.matcher(fen);
        if (matcher.matches())
        {
            yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
        }
        else
        {
            throw new Exception("分转元参数格式不正确!");
        }
        return yuan;
    }

    /**
     * 元转换为分.
     *
     * @param yuan 元
     * @return 分
     * @throws Exception
     */
    public static String fromYuanToFen(final String yuan)
        throws Exception
    {
        String fen = "";
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?{1}");
        Matcher matcher = pattern.matcher(yuan);
        if (matcher.matches())
        {
            try
            {
                NumberFormat format = NumberFormat.getInstance();
                Number number = format.parse(yuan);
                double temp = number.doubleValue() * 100.0;
                // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
                format.setGroupingUsed(false);
                // 设置返回数的小数部分所允许的最大位数
                format.setMaximumFractionDigits(0);
                fen = format.format(temp);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // throw new Exception("元转分参数格式不正确!");
            fen = String.valueOf(Integer.valueOf(yuan) * 100);
        }
        return fen;
    }

    public static void main(String[] args)
        throws Exception
    {
        String s = "0.01";
        Integer.valueOf(s);
        System.out.println(fromYuanToFen(s));
    }
}
