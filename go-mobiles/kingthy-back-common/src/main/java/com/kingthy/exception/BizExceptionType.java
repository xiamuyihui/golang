package com.kingthy.exception;

/**
 * 
 *
 * BizExceptionType
 * 
 * 潘勇 2017年4月24日 上午10:24:51
 * 
 * @version 1.0.0 运营平台异常 1001 <br>
 *          订单模块异常 1002 <br>
 *          用户模块异常 1003 <br>
 *          支付模块异常 1004 <br>
 *          产品模块异常 1005 <br>
 *          作品模块异常 1006 <br>
 *          商品模块异常 1007 <br>
 *          购物车模块异常 1008 <br>
 *          用户注册/登录模块异常 1009  <br>
 */
public enum BizExceptionType
{
    platformException("1001:"), orderException("1002:"), userCenterException("1003:"), paymentException(
        "1004:"), produceException("1005:"), opusException("1006:"), goodsException("1007:"), cartException("1008:")
    ,userRegisterException("1009:"),userFacadeImplException("1009:");
    private final String value;
    
    BizExceptionType(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }
}
