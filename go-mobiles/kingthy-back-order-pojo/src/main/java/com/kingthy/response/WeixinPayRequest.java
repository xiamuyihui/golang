package com.kingthy.response;

import java.io.Serializable;

/**
 * @author:xumin
 * @Description:
 * @Date:9:36 2017/11/3
 */
public class WeixinPayRequest implements Serializable
{

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */

    private static final long serialVersionUID = 8294457695940754960L;

    /**
     * 签名 sign 是 String(32)
     */
    private String sign;

    /**
     * 商品描述 body 是 String(128)
     */
    private String body;

    /**
     * 商品详情 detail 否 String(8192)
     */
    private String detail;

    /**
     * 附加数据 attach 否 String(127)
     */
    private String attach;

    /**
     * 商户订单号 out_trade_no 是 String(32)
     */
    private String out_trade_no;

    /**
     * 总金额 total_fee 是 Int
     */
    private String total_fee;

    /**
     * 终端IP spbill_create_ip 是 String(16)
     */
    private String spbill_create_ip;

    /**
     * 交易起始时间 time_start 否 String(14)
     */
    private String time_start;

    /**
     * 交易结束时间 time_expire 否 String(14)
     */
    private String time_expire;

    /**
     * 获取签名sign是String(32)
     *
     * @return sign 签名sign是String(32)
     */

    public String getSign()
    {
        return sign;
    }

    /**
     * 设置签名sign是String(32)
     *
     * @param sign 签名sign是String(32)
     */
    public void setSign(String sign)
    {
        this.sign = sign;
    }

    /**
     * 获取商品描述body是String(128)
     *
     * @return body 商品描述body是String(128)
     */

    public String getBody()
    {
        return body;
    }

    /**
     * 设置商品描述body是String(128)
     *
     * @param body 商品描述body是String(128)
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * 获取商品详情detail否String(8192)
     *
     * @return detail 商品详情detail否String(8192)
     */

    public String getDetail()
    {
        return detail;
    }

    /**
     * 设置商品详情detail否String(8192)
     *
     * @param detail 商品详情detail否String(8192)
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * 获取附加数据attach否String(127)
     *
     * @return attach 附加数据attach否String(127)
     */

    public String getAttach()
    {
        return attach;
    }

    /**
     * 设置附加数据attach否String(127)
     *
     * @param attach 附加数据attach否String(127)
     */
    public void setAttach(String attach)
    {
        this.attach = attach;
    }

    /**
     * 获取商户订单号out_trade_no是String(32)
     *
     * @return out_trade_no 商户订单号out_trade_no是String(32)
     */

    public String getOut_trade_no()
    {
        return out_trade_no;
    }

    /**
     * 设置商户订单号out_trade_no是String(32)
     *
     * @param out_trade_no 商户订单号out_trade_no是String(32)
     */
    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
    }

    /**
     * 获取总金额total_fee是Int
     *
     * @return total_fee 总金额total_fee是Int
     */

    public String getTotal_fee()
    {
        return total_fee;
    }

    /**
     * 设置总金额total_fee是Int
     *
     * @param total_fee 总金额total_fee是Int
     */
    public void setTotal_fee(String total_fee)
    {
        this.total_fee = total_fee;
    }

    /**
     * 获取终端IPspbill_create_ip是String(16)
     *
     * @return spbill_create_ip 终端IPspbill_create_ip是String(16)
     */

    public String getSpbill_create_ip()
    {
        return spbill_create_ip;
    }

    /**
     * 设置终端IPspbill_create_ip是String(16)
     *
     * @param spbill_create_ip 终端IPspbill_create_ip是String(16)
     */
    public void setSpbill_create_ip(String spbill_create_ip)
    {
        this.spbill_create_ip = spbill_create_ip;
    }

    /**
     * 获取交易起始时间time_start否String(14)
     *
     * @return time_start 交易起始时间time_start否String(14)
     */

    public String getTime_start()
    {
        return time_start;
    }

    /**
     * 设置交易起始时间time_start否String(14)
     *
     * @param time_start 交易起始时间time_start否String(14)
     */
    public void setTime_start(String time_start)
    {
        this.time_start = time_start;
    }

    /**
     * 获取交易结束时间time_expire否String(14)
     *
     * @return time_expire 交易结束时间time_expire否String(14)
     */

    public String getTime_expire()
    {
        return time_expire;
    }

    /**
     * 设置交易结束时间time_expire否String(14)
     *
     * @param time_expire 交易结束时间time_expire否String(14)
     */
    public void setTime_expire(String time_expire)
    {
        this.time_expire = time_expire;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "WeixinPayBean [sign=" + sign + ", body=" + body + ", detail=" + detail + ", attach=" + attach
            + ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip=" + spbill_create_ip
            + ", time_start=" + time_start + ", time_expire=" + time_expire + "]";
    }

}
