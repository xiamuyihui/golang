package com.kingthy.constant;

/**
 * @author xumin
 * @Description: 项目所有的 queues exchanges
 * @DATE Created by 16:01 on 2017/5/26.
 * @Modified by:
 */
public class RabbitmqConstants {

    private static final String BASE_DLQ = "dlq";
    private static final String QUEUE_SUFFIX = ".queue";
    private static final String TOPIC_SUFFIX = ".topic";
    private static final String ROUTING_SUFFIX = ".routing";

    /**
     * 买家秀过滤
     */
    private static final String BUYERS_SHOW_KEY = "kingthy.buyers.show";
    private static final String DLQ_BUYERS_SHOW_KEY = BASE_DLQ + "." + BUYERS_SHOW_KEY;
    public static final String BUYERS_SHOW_LISTENER_QUEUE = BUYERS_SHOW_KEY + QUEUE_SUFFIX;
//    public static final String BUYERS_SHOW_LISTENER_QUEUE = BuyersShowEnum.QUEUE.toString();

    public enum BuyersShowEnum{

        /**
         * @author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(BUYERS_SHOW_LISTENER_QUEUE),EXCHANGE(BUYERS_SHOW_KEY + TOPIC_SUFFIX),ROUTING(BUYERS_SHOW_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_BUYERS_SHOW_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_BUYERS_SHOW_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_BUYERS_SHOW_KEY + ROUTING_SUFFIX);

        private String value;

        BuyersShowEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }


    /**
     * 商品信息同步es
     */
    private static final String ES_GOODS_KEY = "kingthy.es.goods";
    private static final String DLQ_ES_GOODS_KEY = BASE_DLQ + "." + ES_GOODS_KEY;
    public static final String ES_GOODS_LISTENER_QUEUE = ES_GOODS_KEY + QUEUE_SUFFIX;

    public enum EsGoodsEnum{

        /**
         * @author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(ES_GOODS_LISTENER_QUEUE),EXCHANGE(ES_GOODS_KEY + TOPIC_SUFFIX),ROUTING(ES_GOODS_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_ES_GOODS_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_ES_GOODS_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_ES_GOODS_KEY + ROUTING_SUFFIX);

        private String value;

        EsGoodsEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 更新作品状态
     */
    private static final String UPDATE_OPUS_KEY = "kingthy.update.opus";
    private static final String DLQ_UPDATE_OPUS_KEY = BASE_DLQ + "." + UPDATE_OPUS_KEY;
    public static final String UPDATE_OPUS_LISTENER_QUEUE = UPDATE_OPUS_KEY + QUEUE_SUFFIX;

    public enum UpdateOpusEnum{

        /**
         * @Author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(UPDATE_OPUS_LISTENER_QUEUE),EXCHANGE(UPDATE_OPUS_KEY + TOPIC_SUFFIX),ROUTING(UPDATE_OPUS_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_UPDATE_OPUS_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_UPDATE_OPUS_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_UPDATE_OPUS_KEY + ROUTING_SUFFIX);

        private String value;

        UpdateOpusEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 订单确认收货 增加会员收益
     */
    private static final String ORDER_INCOME_KEY = "kingthy.order.income";
    private static final String DLQ_ORDER_INCOME_KEY = BASE_DLQ + "." + ORDER_INCOME_KEY;
    public static final String ORDER_INCOME_LISTENER_QUEUE = ORDER_INCOME_KEY + QUEUE_SUFFIX;

    public enum OrderIncomeEnum{

        /**
         * @author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(ORDER_INCOME_LISTENER_QUEUE),EXCHANGE(ORDER_INCOME_KEY + TOPIC_SUFFIX),ROUTING(ORDER_INCOME_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_ORDER_INCOME_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_ORDER_INCOME_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_ORDER_INCOME_KEY + ROUTING_SUFFIX);

        private String value;

        OrderIncomeEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 上传模型图片到服务器
     */
    private static final String MODEL_IMAGE_KEY = "kingthy.model.image";
    private static final String DLQ_MODEL_IMAGE_KEY = BASE_DLQ + "." + MODEL_IMAGE_KEY;
    public static final String MODEL_IMAGE_LISTENER_QUEUE = MODEL_IMAGE_KEY + QUEUE_SUFFIX;


    public enum UploadModelImageEnum{

        /**
         * @Author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(MODEL_IMAGE_LISTENER_QUEUE),EXCHANGE(MODEL_IMAGE_KEY + TOPIC_SUFFIX),ROUTING(MODEL_IMAGE_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_MODEL_IMAGE_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_MODEL_IMAGE_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_MODEL_IMAGE_KEY + ROUTING_SUFFIX);

        private String value;

        UploadModelImageEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
/**
 * 会员提现
 */

    private static final String WITHDRAWS_CASH_KEY = "kingthy.withdraws.cash";
    private static final String DLQ_WITHDRAWS_CASH_KEY = BASE_DLQ + "." + WITHDRAWS_CASH_KEY;
    public static final String WITHDRAWS_CASH_LISTENER_QUEUE = WITHDRAWS_CASH_KEY + QUEUE_SUFFIX;

    public enum WithdrawsCashEnum{
        /**
         * @author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(WITHDRAWS_CASH_LISTENER_QUEUE),EXCHANGE(WITHDRAWS_CASH_KEY + TOPIC_SUFFIX),ROUTING(WITHDRAWS_CASH_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_WITHDRAWS_CASH_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_WITHDRAWS_CASH_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_WITHDRAWS_CASH_KEY + ROUTING_SUFFIX);

        private String value;

        WithdrawsCashEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 发送短信
     */
    private static final String SMS_MESSAGE_KEY = "kingthy.sms.message";
    private static final String DLQ_SMS_MESSAGE_KEY = BASE_DLQ + "." + SMS_MESSAGE_KEY;
    public static final String SMS_MESSAGE_LISTENER_QUEUE = SMS_MESSAGE_KEY + QUEUE_SUFFIX;

    public enum SmsMessageEnum{

        /**
         * @Author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(SMS_MESSAGE_LISTENER_QUEUE),QUEUE_A(SMS_MESSAGE_LISTENER_QUEUE + "_A"),
        EXCHANGE(SMS_MESSAGE_KEY + TOPIC_SUFFIX),ROUTING(SMS_MESSAGE_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_SMS_MESSAGE_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_SMS_MESSAGE_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_SMS_MESSAGE_KEY + ROUTING_SUFFIX);

        private String value;

        SmsMessageEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 订单事件队列
     */

    private static final String ORDER_EVENT_KEY = "kingthy.order.event";
    private static final String DLQ_ORDER_EVENT_KEY = BASE_DLQ + "." + ORDER_EVENT_KEY;
    public static final String ORDER_EVENT_LISTENER_QUEUE = ORDER_EVENT_KEY + QUEUE_SUFFIX;

    public enum OrderEventEnum
    {
        /**
         * @author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(ORDER_EVENT_LISTENER_QUEUE),EXCHANGE(ORDER_EVENT_KEY + TOPIC_SUFFIX),ROUTING(ORDER_EVENT_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_ORDER_EVENT_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_ORDER_EVENT_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_ORDER_EVENT_KEY + ROUTING_SUFFIX);

        private String value;

        OrderEventEnum(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 面辅料队列
     */

    private static final String MATERIAL_ACCESSORIES_KEY = "kingthy.material.accessories";
    private static final String DLQ_MATERIAL_ACCESSORIES_KEY = BASE_DLQ + "." + MATERIAL_ACCESSORIES_KEY;
    public static final String MATERIAL_ACCESSORIES_LISTENER_QUEUE = MATERIAL_ACCESSORIES_KEY + QUEUE_SUFFIX;

    public enum MaterialAccessories
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:10:28 2017/11/2
         */
        QUEUE(MATERIAL_ACCESSORIES_LISTENER_QUEUE),EXCHANGE(MATERIAL_ACCESSORIES_KEY + TOPIC_SUFFIX),ROUTING(MATERIAL_ACCESSORIES_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_MATERIAL_ACCESSORIES_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_MATERIAL_ACCESSORIES_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_MATERIAL_ACCESSORIES_KEY + ROUTING_SUFFIX);

        private String value;

        MaterialAccessories(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /**
     * 创建通知CIPP订单数据的队列
     */

    private static final String NOTIFY_CIPP_KEY = "kingthy.notify.cipp";
    private static final String DLQ_NOTIFY_CIPP_KEY = BASE_DLQ + "." + NOTIFY_CIPP_KEY;
    public static final String NOTIFY_CIPP_LISTENER_QUEUE = NOTIFY_CIPP_KEY + QUEUE_SUFFIX;

    public enum NotifyCipp
    {
        /**
         * @author:xumin
         * @Description:
         * @Date:10:30 2017/11/2
         */
        QUEUE(NOTIFY_CIPP_LISTENER_QUEUE),EXCHANGE(NOTIFY_CIPP_KEY + TOPIC_SUFFIX),ROUTING(NOTIFY_CIPP_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_NOTIFY_CIPP_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_NOTIFY_CIPP_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_NOTIFY_CIPP_KEY + ROUTING_SUFFIX);

        private String value;

        NotifyCipp(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /*************************************创建订单队列 begin **************************************/

    private static final String MALL_CREATE_ORDER_KEY = "kingthy.mall.order";
    private static final String DLQ_MALL_CREATE_KEY = BASE_DLQ + "." + MALL_CREATE_ORDER_KEY;
    public static final String MALL_CREATE_LISTENER_QUEUE = MALL_CREATE_ORDER_KEY + QUEUE_SUFFIX;

    public enum MallCreateOrder
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:56 2017/11/13
         */
        QUEUE(MALL_CREATE_LISTENER_QUEUE),EXCHANGE(MALL_CREATE_ORDER_KEY + TOPIC_SUFFIX),ROUTING(MALL_CREATE_ORDER_KEY + ROUTING_SUFFIX),
        DLQ_QUEUE(DLQ_MALL_CREATE_KEY + QUEUE_SUFFIX),DLQ_EXCHANGE(DLQ_MALL_CREATE_KEY + TOPIC_SUFFIX),DLQ_ROUTING(DLQ_MALL_CREATE_KEY + ROUTING_SUFFIX);

        private String value;

        MallCreateOrder(String value){
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /*************************************创建订单队列 end ***************************************/



    /*********纸样裁床文件start****/
    private static final String  NCPLT_FILE_KEY = "kingthy.ncplt.file";
    /**队列****/
    public static final  String  NCPLT_FILE_QUEUE = NCPLT_FILE_KEY + QUEUE_SUFFIX;
    /**交换器****/
    public static final  String  NCPLT_FILE_EXCHANGE = NCPLT_FILE_KEY + TOPIC_SUFFIX;
    /**路由key****/
    public static final  String  NCPLT_FILE_ROUTING = NCPLT_FILE_KEY + ROUTING_SUFFIX;
    /*********裁床文件end****/



    /*********渲染走秀视频start****/
    private static final String  RENDER_VIDEO_KEY = "clothing.rander.video";
    /**队列****/
    public static final  String  RENDER_VIDEO_QUEUE=RENDER_VIDEO_KEY+QUEUE_SUFFIX;
    /**交换器****/
    public static final  String  RENDER_VIDEO_EXCHANGE=RENDER_VIDEO_KEY+TOPIC_SUFFIX;
    /**路由key****/
    public static final  String  RENDER_VIDEO_ROUTING=RENDER_VIDEO_KEY+ROUTING_SUFFIX;
    /*********渲染走秀视频end****/

}
