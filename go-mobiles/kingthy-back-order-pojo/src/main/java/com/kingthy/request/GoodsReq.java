package com.kingthy.request;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * GoodsEditParam(商品修改参数)
 * 
 * @author 陈钊 2017年4月7日 下午4:00:25
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class GoodsReq implements Serializable
{
    
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    

    private String goodsName;
    
    private String goodsFeature;

    private BigDecimal standardPrice;
    
    private String opusSn;
    
    private String desinger;
    
    private Integer returnPoint;
    
    private Integer putOnMethod;
    
    private Date putOnTime;
    
    private Integer status;
    
    private String goodsCategoryUuid;
    
    private String goodsStyleUuid;
    
    private String goodsSeasonUuid;
    
    private String goodsDetails;
    
    private String opusUuid;
    
    private String goodsTags;
    
    private String goodsImage;
    
    private String partInfo;
    
    private String materielInfo;
    
    private String accessoriesInfo;
    
    private String uuid;
    
    /**
     * 操作
     */
    private GoodsReq.GoodsOperation goodsOperation;

    public enum GoodsOperation
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:48 2017/11/2
         */
        up(1, "上架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:48 2017/11/2
         */
        down(0, "下架");
        private int value;

        private String nameValue;

        private GoodsOperation(int value, String nameValue)
        {
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }
    }

    /**
     * 上架方式
     */
    private GoodsReq.GoodsPutOnMethod goodsPutOnMethod;

    public enum GoodsPutOnMethod
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:49 2017/11/2
         */
        immediately(0, "立即上架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:49 2017/11/2
         */
        wait(1, "延时上架");
        private int value;

        private String nameValue;

        private GoodsPutOnMethod(int value, String nameValue)
        {
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }

    }

    /**
     * 商品上架状态
     */
    private GoodsReq.GoodsStatus goodsStatus;
    
    public enum GoodsStatus
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:49 2017/11/2
         */
        goodsOn(1, "已上架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:49 2017/11/2
         */
        goodsDown(0, "已下架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:49 2017/11/2
         */
        goodsWait(2, "延时上架");
        private int value;
        
        private String nameValue;
        
        private GoodsStatus(int value, String nameValue)
        {
            this.nameValue = nameValue;
            this.value = value;
        }
        
        public int getValue()
        {
            return value;
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        public String getNameValue()
        {
            return nameValue;
        }
        
        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }
        
    }
    
}
