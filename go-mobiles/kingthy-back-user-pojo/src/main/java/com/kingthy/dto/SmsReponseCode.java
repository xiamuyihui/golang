package com.kingthy.dto;

/**
 * 描述：短信响应码
 *
 * @author likejie
 * @date 2017/11/1
 */
public enum  SmsReponseCode {
    /**
     * 发送成功
     */
    SUCCESS("02");

    private String value;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    SmsReponseCode(String val){
        this.value=val;
    }

}
