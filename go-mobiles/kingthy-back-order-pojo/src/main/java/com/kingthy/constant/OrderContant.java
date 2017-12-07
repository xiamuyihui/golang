package com.kingthy.constant;

/**
 * 支付常量类
 *
 * GoodsAndOrderContant
 * 
 * @author xumin 2017年4月19日 下午11:57:52
 * 
 * @version 1.0.0
 *
 */
public class OrderContant
{
    /**
     * 生成订单类型 0 商品生成订单 1 购物车生成订单
     */
    public enum CreateOrderType
    {
        /**
         * 商品生成订单
         */
        PRODUCTTYPE(0),
        /**
         * 购物车生成订单
         */
        CARTTYPE(1);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private CreateOrderType(int value)
        {
            this.value = value;
        }
    }

    /**
     * 订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单
     */
    public enum OrderStatusType{

    }

}
