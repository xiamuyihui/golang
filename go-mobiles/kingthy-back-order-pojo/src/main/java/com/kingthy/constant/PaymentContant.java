package com.kingthy.constant;

/**
 * 支付常量类
 *
 * PaymentContant
 * 
 * @author 潘勇 2017年1月13日 下午5:49:52
 * 
 * @version 1.0.0
 *
 */
public class PaymentContant
{
    public enum PayStatus
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:24 2017/11/2
         */
        PAY_SUCCESS("9000"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:24 2017/11/2
         */
        ORDER_PROCESS("8000"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:25 2017/11/2
         */
        USER_CANCEL("6001"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:25 2017/11/2
         */
        NET_ERROR("6002"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:25 2017/11/2
         */
        PAY_FAILE("4000");
        private String value;
        
        public String getValue()
        {
            return value;
        }
        
        public void setValue(String value)
        {
            this.value = value;
        }
        
        private PayStatus(String value)
        {
            this.value = value;
        }
    }
}
