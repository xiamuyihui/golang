package com.kingthy.exception.bizexcapiton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kingthy.exception.BizException;
import com.kingthy.exception.BizExceptionType;

/**
 * 账户服务业务异常类,异常代码8位数字组成,前4位固定2008打头,后4位自定义
 *
 * CartBizException
 * 
 * @author pany 2016年5月4日 上午11:02:00
 * 
 * @version 1.0.0
 *
 */
public class CartBizException extends BizException
{
    
    private static final long serialVersionUID = 1L;
    
    private static final Log log = LogFactory.getLog(CartBizException.class);
    
    private static final String  PREFIXEXCEPTION = BizExceptionType.cartException.getValue();
    
    public CartBizException()
    {
    }
    
    public CartBizException(String code, String msgFormat, Object... args)
    {
        super(PREFIXEXCEPTION + code, msgFormat, args);
    }
    
    public CartBizException(String code, String msg)
    {
        super(PREFIXEXCEPTION + code, msg);
    }
    
    /**
     * 实例化异常
     */
    @Override
    public CartBizException newInstance(String msgFormat, Object... args)
    {
        return new CartBizException(this.code, msgFormat, args);
    }
    
    public CartBizException print()
    {
        log.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return new CartBizException(this.code, this.msg);
    }
}
