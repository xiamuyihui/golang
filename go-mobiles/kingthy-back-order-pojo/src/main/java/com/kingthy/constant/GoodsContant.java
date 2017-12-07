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
public class GoodsContant
{
    /**
     * 商品上架状态：0：未上架，1：已上架，2：延迟上架
     */
    public enum GooodsStatusType
    {
        /**
         * 上架
         */
        GOOODS_SALE_Y(1),

        /**
         * 未上架
         */
        GOOODS_SALE_N(0),

        /**
         * 延迟上架
         */
        GOOODS_SALE_D(2);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private GooodsStatusType(int value)
        {
            this.value = value;
        }
    }
}
