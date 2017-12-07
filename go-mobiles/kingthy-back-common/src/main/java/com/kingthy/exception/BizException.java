package com.kingthy.exception;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 
 * @author pany
 * 
 *         定义异常时，需要先确定异常所属模块。例如：添加订单报错 可以定义为 [10020001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 *         运营平台异常 1001 <br>
 *         订单模块异常 1002 <br>
 *         用户模块异常 1003 <br>
 *         支付模块异常 1004 <br>
 *         产品模块异常 1005 <br>
 *         作品模块异常 1006 <br>
 *         商品模块异常 1007 <br>
 *         购物车模块异常 1008 <br>
 * 
 */
public class BizException extends RuntimeException
{
    
    private static final long serialVersionUID = -5875371379845226068L;
    
    /**
     * 数据库操作,insert返回0
     */
    public static final BizException DB_INSERT_RESULT_0 = new BizException("90040001", "数据库操作,insert返回0");
    
    /**
     * 数据库操作,update返回0
     */
    public static final BizException DB_UPDATE_RESULT_0 = new BizException("90040002", "数据库操作,update返回0");
    
    /**
     * 数据库操作,selectOne返回null
     */
    public static final BizException DB_SELECTONE_IS_NULL = new BizException("90040003", "数据库操作,selectOne返回null");
    
    /**
     * 数据库操作,list返回null
     */
    public static final BizException DB_LIST_IS_NULL = new BizException("90040004", "数据库操作,list返回null");
    
    public static final BizException ACCOUNT_EMPTY = new BizException("9004005", "数据库操作,账号不存在");
    
    /**
     * 异常信息
     */
    protected String msg;
    
    /**
     * 具体异常码
     */
    protected String code;
    
    public BizException(String code, String msgFormat, Object... args)
    {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }
    
    public BizException()
    {
        super();
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public String getCode()
    {
        return code;
    }
    
    /**
     * 实例化异常 newInstance(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param msgFormat
     * @param args
     * @return BizException
     * @author pany
     * @exception @since 1.0.0
     */
    public BizException newInstance(String msgFormat, Object... args)
    {
        return new BizException(this.code, msgFormat, args);
    }
    
    public BizException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BizException(Throwable cause)
    {
        super(cause);
    }
    
    public BizException(String message)
    {
        super(message);
    }
}
