package com.kingthy.dto;



/**
 * 请求码
 * @author  likejie  2017/11/1.
 */
public enum WsRequestCode {

    /**
     * 登录
     */
    LOGIN(0),
    /**
     * 转发，推送
     */
    FORWORD(1),
    /**
     * 二维码登录授权
     */
    QRCODE_LOGIN_AUTH(2),
    /**
     * 系统广播
     */
    BRAODCAST(3),
    NONE(4);
    WsRequestCode(int val){
        this.value=val;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
