package com.kingthy.dto;


/**
 * 应答码
 * @author  likejie  2017/11/1.
 */
public enum WsResponseCode {

    //连接成功
    SUCCESS(0),
    //连接失败
    FALI(1),
    //允许二维码登录，并返回token
    QRCODE_LOGIN_SUCCESS(2),
    //系统异常
    EXCEPTION(3),
    //推送信息
    INFO(4),
    //用户token错误
    TOKEN_ERROR(5),
    //消息格式不正确，解析错误
    PARSE_ERROR(6),
    //connectid 错误
    CONNECTID_ERROR(7);

    WsResponseCode(int val){
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
