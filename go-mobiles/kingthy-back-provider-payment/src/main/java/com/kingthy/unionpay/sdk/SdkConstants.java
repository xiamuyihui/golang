/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       MPI插件包常量定义
 * =============================================================================
 */
package com.kingthy.unionpay.sdk;
/**
 * @author
 * @ClassName SdkConstants
 * @Description acpsdk常量类
 * @date 2016-7-22 下午4:05:54
 *
 */
public class SdkConstants
{

	public final static String COLUMN_DEFAULT = "-";

	public final static String KEY_DELIMITER = "#";

	/** memeber variable: blank. */
	public static final String BLANK = "";

	/** member variabel: space. */
	public static final String SPACE = " ";

	/** memeber variable: unline. */
	public static final String UNLINE = "_";

	/** memeber varibale: star. */
	public static final String STAR = "*";

	/** memeber variable: line. */
	public static final String LINE = "-";

	/** memeber variable: add. */
	public static final String ADD = "+";

	/** memeber variable: colon. */
	public final static String COLON = "|";

	/** memeber variable: point. */
	public final static String POINT = ".";

	/** memeber variable: comma. */
	public final static String COMMA = ",";

	/** memeber variable: slash. */
	public final static String SLASH = "/";

	/** memeber variable: div. */
	public final static String DIV = "/";

	/** memeber variable: left . */
	public final static String LB = "(";

	/** memeber variable: right. */
	public final static String RB = ")";

	/** memeber variable: rmb. */
	public final static String CUR_RMB = "RMB";

	/** memeber variable: .page size */
	public static final int PAGE_SIZE = 10;

	/** memeber variable: String ONE. */
	public static final String ONE = "1";

	/** memeber variable: String ZERO. */
	public static final String ZERO = "0";

	/** memeber variable: number six. */
	public static final int NUM_SIX = 6;

	/** memeber variable: equal mark. */
	public static final String EQUAL = "=";

	/** memeber variable: operation ne. */
	public static final String NE = "!=";

	/** memeber variable: operation le. */
	public static final String LE = "<=";

	/** memeber variable: operation ge. */
	public static final String GE = ">=";

	/** memeber variable: operation lt. */
	public static final String LT = "<";

	/** memeber variable: operation gt. */
	public static final String GT = ">";

	/** memeber variable: list separator. */
	public static final String SEP = "./";

	/** memeber variable: Y. */
	public static final String Y = "Y";

	/** memeber variable: AMPERSAND. */
	public static final String AMPERSAND = "&";

	/** memeber variable: SQL_LIKE_TAG. */
	public static final String SQL_LIKE_TAG = "%";

	/** memeber variable: @. */
	public static final String MAIL = "@";

	/** memeber variable: number zero. */
	public static final int NZERO = 0;

	public static final String LEFT_BRACE = "{";

	public static final String RIGHT_BRACE = "}";

	/** memeber variable: string true. */
	public static final String TRUE_STRING = "true";
	/** memeber variable: string false. */
	public static final String FALSE_STRING = "false";

	/** memeber variable: forward success. */
	public static final String SUCCESS = "success";
	/** memeber variable: forward fail. */
	public static final String FAIL = "fail";
	/** memeber variable: global forward success. */
	public static final String GLOBAL_SUCCESS = "$success";
	/** memeber variable: global forward fail. */
	public static final String GLOBAL_FAIL = "$fail";

	public static final String UTF_8_ENCODING = "UTF-8";
	public static final String GBK_ENCODING = "GBK";
	public static final String CONTENT_TYPE = "Content-type";
	public static final String APP_XML_TYPE = "application/xml;charset=utf-8";
	public static final String APP_FORM_TYPE = "application/x-www-form-urlencoded;charset=";
	
	public static final String VERSION_1_0_0 = "1.0.0";
	public static final String VERSION_5_0_0 = "5.0.0";
	public static final String VERSION_5_0_1 = "5.0.1";
	public static final String VERSION_5_1_0 = "5.1.0";
	public static final String SIGNMETHOD_RSA = "01";
	public static final String SIGNMETHOD_SHA256 = "11";
	public static final String SIGNMETHOD_SM3 = "12";
	public static final String UNIONPAY_CNNAME = "中国银联股份有限公司";

	/**
	 * 敏感信息加密公钥
	 */
	public static final String CERTTYPE_01 = "01";

	/**
	 * 磁道加密公钥
	 */
	public static final String CERTTYPE_02 = "02";

	/******************************************** 5.0报文接口定义 ********************************************/
	/** 版本号. */
	public static final String PARAM_VERSION = "version";
	/** 证书ID. */
	public static final String PARAM_CERT_ID = "certId";
	/** 签名. */
	public static final String PARAM_SIGNATURE = "signature";
	/** 签名方法. */
	public static final String PARAM_SIGN_METHOD = "signMethod";
	/** 编码方式. */
	public static final String PARAM_ENCODING = "encoding";
	/** 交易类型. */
	public static final String PARAM_TXN_TYPE = "txnType";
	/** 交易子类. */
	public static final String PARAM_TXN_SUB_TYPE = "txnSubType";
	/** 业务类型. */
	public static final String PARAM_BIZ_TYPE = "bizType";
	/** 前台通知地址 . */
	public static final String PARAM_FRONT_URL = "frontUrl";
	/** 后台通知地址. */
	public static final String PARAM_BACK_URL = "backUrl";
	/** 接入类型. */
	public static final String PARAM_ACCESS_TYPE = "accessType";
	/** 收单机构代码. */
	public static final String PARAM_ACQ_INS_CODE = "acqInsCode";
	/** 商户类别. */
	public static final String PARAM_MER_CAT_CODE = "merCatCode";
	/** 商户类型. */
	public static final String PARAM_MER_TYPE = "merType";
	/** 商户代码. */
	public static final String PARAM_MER_ID = "merId";
	/** 商户名称. */
	public static final String PARAM_MER_NAME = "merName";
	/** 商户简称. */
	public static final String PARAM_MER_ABBR = "merAbbr";
	/** 二级商户代码. */
	public static final String PARAM_SUB_MER_ID = "subMerId";
	/** 二级商户名称. */
	public static final String PARAM_SUB_MER_NAME = "subMerName";
	/** 二级商户简称. */
	public static final String PARAM_SUB_MER_ABBR = "subMerAbbr";
	/** Cupsecure 商户代码. */
	public static final String PARAM_CS_MER_ID = "csMerId";
	/** 商户订单号. */
	public static final String PARAM_ORDER_ID = "orderId";
	/** 交易时间. */
	public static final String PARAM_TXN_TIME = "txnTime";
	/** 发送时间. */
	public static final String PARAM_TXN_SEND_TIME = "txnSendTime";
	/** 订单超时时间间隔. */
	public static final String PARAM_ORDER_TIMEOUT_INTERVAL = "orderTimeoutInterval";
	/** 支付超时时间. */
	public static final String PARAM_PAY_TIMEOUT_TIME = "payTimeoutTime";
	/** 默认支付方式. */
	public static final String PARAM_DEFAULT_PAY_TYPE = "defaultPayType";
	/** 支持支付方式. */
	public static final String PARAM_SUP_PAY_TYPE = "supPayType";
	/** 支付方式. */
	public static final String PARAM_PAY_TYPE = "payType";
	/** 自定义支付方式. */
	public static final String PARAM_CUSTOM_PAY_TYPE = "customPayType";
	/** 物流标识. */
	public static final String PARAM_SHIPPING_FLAG = "shippingFlag";
	/** 收货地址-国家. */
	public static final String PARAM_SHIPPING_COUNTRY_CODE = "shippingCountryCode";
	/** 收货地址-省. */
	public static final String PARAM_SHIPPING_PROVINCE_CODE = "shippingProvinceCode";
	/** 收货地址-市. */
	public static final String PARAM_SHIPPING_CITY_CODE = "shippingCityCode";
	/** 收货地址-地区. */
	public static final String PARAM_SHIPPING_DISTRICT_CODE = "shippingDistrictCode";
	/** 收货地址-详细. */
	public static final String PARAM_SHIPPING_STREET = "shippingStreet";
	/** 商品总类. */
	public static final String PARAM_COMMODITY_CATEGORY = "commodityCategory";
	/** 商品名称. */
	public static final String PARAM_COMMODITY_NAME = "commodityName";
	/** 商品URL. */
	public static final String PARAM_COMMODITY_URL = "commodityUrl";
	/** 商品单价. */
	public static final String PARAM_COMMODITY_UNIT_PRICE = "commodityUnitPrice";
	/** 商品数量. */
	public static final String PARAM_COMMODITY_QTY = "commodityQty";
	/** 是否预授权. */
	public static final String PARAM_IS_PRE_AUTH = "isPreAuth";
	/** 币种. */
	public static final String PARAM_CURRENCY_CODE = "currencyCode";
	/** 账户类型. */
	public static final String PARAM_ACC_TYPE = "accType";
	/** 账号. */
	public static final String PARAM_ACC_NO = "accNo";
	/** 支付卡类型. */
	public static final String PARAM_PAY_CARD_TYPE = "payCardType";
	/** 发卡机构代码. */
	public static final String PARAM_ISS_INS_CODE = "issInsCode";
	/** 持卡人信息. */
	public static final String PARAM_CUSTOMER_INFO = "customerInfo";
	/** 交易金额. */
	public static final String PARAM_TXN_AMT = "txnAmt";
	/** 余额. */
	public static final String PARAM_BALANCE = "balance";
	/** 地区代码. */
	public static final String PARAM_DISTRICT_CODE = "districtCode";
	/** 附加地区代码. */
	public static final String PARAM_ADDITIONAL_DISTRICT_CODE = "additionalDistrictCode";
	/** 账单类型. */
	public static final String PARAM_BILL_TYPE = "billType";
	/** 账单号码. */
	public static final String PARAM_BILL_NO = "billNo";
	/** 账单月份. */
	public static final String PARAM_BILL_MONTH = "billMonth";
	/** 账单查询要素. */
	public static final String PARAM_BILL_QUERY_INFO = "billQueryInfo";
	/** 账单详情. */
	public static final String PARAM_BILL_DETAIL_INFO = "billDetailInfo";
	/** 账单金额. */
	public static final String PARAM_BILL_AMT = "billAmt";
	/** 账单金额符号. */
	public static final String PARAM_BILL_AMT_SIGN = "billAmtSign";
	/** 绑定标识号. */
	public static final String PARAM_BIND_ID = "bindId";
	/** 风险级别. */
	public static final String PARAM_RISK_LEVEL = "riskLevel";
	/** 绑定信息条数. */
	public static final String PARAM_BIND_INFO_QTY = "bindInfoQty";
	/** 绑定信息集. */
	public static final String PARAM_BIND_INFO_LIST = "bindInfoList";
	/** 批次号. */
	public static final String PARAM_BATCH_NO = "batchNo";
	/** 总笔数. */
	public static final String PARAM_TOTAL_QTY = "totalQty";
	/** 总金额. */
	public static final String PARAM_TOTAL_AMT = "totalAmt";
	/** 文件类型. */
	public static final String PARAM_FILE_TYPE = "fileType";
	/** 文件名称. */
	public static final String PARAM_FILE_NAME = "fileName";
	/** 批量文件内容. */
	public static final String PARAM_FILE_CONTENT = "fileContent";
	/** 商户摘要. */
	public static final String PARAM_MER_NOTE = "merNote";
	/** 商户自定义域. */
	// public static final String param_merReserved = "merReserved";//接口变更删除
	// 新增接口
	/** 请求方保留域. */
	public static final String PARAM_REQ_RESERVED = "reqReserved";
	/** 保留域. */
	public static final String PARAM_RESERVED = "reserved";
	/** 终端号. */
	public static final String PARAM_TERM_ID = "termId";
	/** 终端类型. */
	public static final String PARAM_TERM_TYPE = "termType";
	/** 交互模式. */
	public static final String PARAM_INTERACT_MODE = "interactMode";
	// 接口名称变更
	// public static final String param_recognitionMode = "recognitionMode";
	/** 发卡机构识别模式. */
	public static final String PARAM_ISSUER_IDENTIFY_MODE = "issuerIdentifyMode";
	/** 商户端用户号. */
	public static final String PARAM_MER_USER_ID = "merUserId";
	/** 持卡人IP. */
	public static final String PARAM_CUSTOMER_IP = "customerIp";
	/** 查询流水号. */
	public static final String PARAM_QUERY_ID = "queryId";
	/** 原交易查询流水号. */
	public static final String PARAM_ORIG_QRY_ID = "origQryId";
	/** 系统跟踪号. */
	public static final String PARAM_TRACE_NO = "traceNo";
	/** 交易传输时间. */
	public static final String PARAM_TRACE_TIME = "traceTime";
	/** 清算日期. */
	public static final String PARAM_SETTLE_DATE = "settleDate";
	/** 清算币种. */
	public static final String PARAM_SETTLE_CURRENCY_CODE = "settleCurrencyCode";
	/** 清算金额. */
	public static final String PARAM_SETTLE_AMT = "settleAmt";
	/** 清算汇率. */
	public static final String PARAM_EXCHANGE_RATE = "exchangeRate";
	/** 兑换日期. */
	public static final String PARAM_EXCHANGE_DATE = "exchangeDate";
	/** 响应时间. */
	public static final String PARAM_RESP_TIME = "respTime";
	/** 原交易应答码. */
	public static final String PARAM_ORIG_RESP_CODE = "origRespCode";
	/** 原交易应答信息. */
	public static final String PARAM_ORIG_RESP_MSG = "origRespMsg";
	/** 应答码. */
	public static final String PARAM_RESP_CODE = "respCode";
	/** 应答码信息. */
	public static final String PARAM_RESP_MSG = "respMsg";
	// 新增四个报文字段merUserRegDt merUserEmail checkFlag activateStatus
	/** 商户端用户注册时间. */
	public static final String PARAM_MER_USER_REG_DT = "merUserRegDt";
	/** 商户端用户注册邮箱. */
	public static final String PARAM_MER_USER_EMAIL = "merUserEmail";
	/** 验证标识. */
	public static final String PARAM_CHECK_FLAG = "checkFlag";
	/** 开通状态. */
	public static final String PARAM_ACTIVATE_STATUS = "activateStatus";
	/** 加密证书ID. */
	public static final String PARAM_ENCRYPT_CERT_ID = "encryptCertId";
	/** 用户MAC、IMEI串号、SSID. */
	public static final String PARAM_USER_MAC = "userMac";
	/** 关联交易. */
	// public static final String param_relationTxnType = "relationTxnType";
	/** 短信类型 */
	public static final String PARAM_SMS_TYPE = "smsType";

	/** 风控信息域 */
	public static final String PARAM_RISK_CTRL_INFO = "riskCtrlInfo";

	/** IC卡交易信息域 */
	public static final String PARAM_IC_TRANS_DATA = "ICTransData";

	/** VPC交易信息域 */
	public static final String PARAM_VPC_TRANS_DATA = "VPCTransData";

	/** 安全类型 */
	public static final String PARAM_SECURITY_TYPE = "securityType";

	/** 银联订单号 */
	public static final String PARAM_TN = "tn";

	/** 分期付款手续费率 */
	public static final String PARAM_INSTAL_RATE = "instalRate";

	/** 分期付款手续费率 */
	public static final String PARAM_MCHNT_FEE_SUBSIDY = "mchntFeeSubsidy";
	
	/** 签名公钥证书 */
	public static final String PARAM_SIGN_PUB_KEY_CERT = "signPubKeyCert";

	/** 加密公钥证书 */
	public static final String PARAM_ENCRYPT_PUB_KEY_CERT = "encryptPubKeyCert";
	
	/** 证书类型 */
	public static final String PARAM_CERT_TYPE = "certType";

}
