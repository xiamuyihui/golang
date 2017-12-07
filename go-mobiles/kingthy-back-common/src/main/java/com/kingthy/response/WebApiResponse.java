package com.kingthy.response;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WebApiResponse<T> implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = -6557898536448299915L;
    
    public static final int SUCCESS_CODE = 0;
    
    public static final int ERROR_CODE = 1;
    
    private int code;
    
    private String message;
    
    private T data;
    
    public static <T> WebApiResponse<T> success()
    {
        WebApiResponse<T> response = new WebApiResponse<T>();
        response.setCode(SUCCESS_CODE);
        response.setMessage("success");
        return response;
    }
    
    public static <T> WebApiResponse<T> success(T data)
    {
        WebApiResponse<T> response = new WebApiResponse<T>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }
    
    public static <T> WebApiResponse<T> error(String message)
    {
        WebApiResponse<T> response = new WebApiResponse<T>();
        response.setCode(ERROR_CODE);
        response.setMessage(message);
        return response;
    }
    
    public WebApiResponse()
    {
        
    }
    
    public WebApiResponse(int code, String message, T data)
    {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * api消息
     */
    public  enum ResponseMsg
    {
        
        SUCCESS("操作成功"),
        FAIL("操作失败"),
        TOKEN_ERROR("token错误"),
        INVALID_CLIENT_ID("无效的clientID"),
        PARAMETER_ERROR("参数为空或参数有误"),
        NOT_LOGIN("用户未登陆"),
        UNEXIST_USER("用户不存在"),
        LOGOUT_ERROR("注销失败"),
        SERVICE_UNAVAILABLE("无法访问服务，该服务可能由于某种未知原因被关闭，请重启服务！"),
        EXCEPTION("接口发生异常"),
        TIMEOUT("执行超时"),
        HYSTRIX_EXCEPTION("发生异常，进入熔断处理");
        
        private String value;
        
        public String getValue()
        {
            return value;
        }
        
        public void setValue(String value)
        {
            this.value = value;
        }
        
        ResponseMsg(String value)
        {
            this.value = value;
        }
    }
}
