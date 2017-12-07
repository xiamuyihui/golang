// package com.kingthy.entity;
//
// import java.io.Serializable;
// import java.math.BigDecimal;
// import java.util.Date;
//
// public class Order implements Serializable
// {
// private Long id;
//
// private Date createDate;
//
// private Date modifyDate;
//
// private Long version;
//
// private String address;
//
// private BigDecimal amount;
//
// private BigDecimal amountPaid;
//
// private String areaName;
//
// private Date completeDate;
//
// private String consignee;
//
// private BigDecimal couponDiscount;
//
// private Long exchangePoint;
//
// private Date expire;
//
// private BigDecimal fee;
//
// private BigDecimal freight;
//
// private String invoiceContent;
//
// private String invoiceTitle;
//
// private Boolean isAllocatedStock;
//
// private Boolean isExchangePoint;
//
// private Boolean isUseCouponCode;
//
// private Date lockExpire;
//
// private String lockKey;
//
// private String memo;
//
// private BigDecimal offsetAmount;
//
// private String paymentMethodName;
//
// private Integer paymentMethodType;
//
// private String phone;
//
// private BigDecimal price;
//
// private BigDecimal promotionDiscount;
//
// private Integer quantity;
//
// private BigDecimal refundAmount;
//
// private Integer returnedQuantity;
//
// private Long rewardPoint;
//
// private Integer shippedQuantity;
//
// private String shippingMethodName;
//
// private String sn;
//
// private Integer status;
//
// private BigDecimal tax;
//
// private Integer type;
//
// private Integer weight;
//
// private String zipCode;
//
// private Long areaId;
//
// private Long couponId;
//
// private Long memberId;
//
// private Long paymentMethodId;
//
// private Long shippingMethodId;
//
// private String promotionNames;
//
// private static final long serialVersionUID = 1L;
//
// /**
// * 状态
// */
// public enum OrderStatus
// {
//
// /** 等待付款 */
// pendingPayment(0, "待付款"),
//
// /** 等待审核 */
// pendingReview(1, "待审核"),
//
// /** 等待发货 */
// pendingShipment(2, "待发货"),
//
// /** 发货 */
// shipped(3, "已发货"),
//
// /** 已收货 */
// received(4, "已收货"),
//
// /** 已完成 */
// completed(5, "已完成"),
//
// /** 已失败 */
// failed(6, "已失败"),
//
// /** 已取消 */
// canceled(7, "已取消"),
//
// /** 已拒绝 */
// denied(8, "已拒绝"),
//
// /** 生产中 */
// producing(9, "生产中"),
// /** 已删除 **/
// delete(12, "已删除"),
// /** 延长订单 **/
// delay(11, "已延长"),
//
// /** 待排产 **/
// waitProduct(12, "待排产"),
// /**
// * 入库待打包
// */
// wareHousing(13, "入库待打包");
// private int value;
//
// private String nameValue;
//
// public int getValue()
// {
// return value;
// }
//
// public void setValue(int value)
// {
// this.value = value;
// }
//
// private OrderStatus(int value, String name)
// {
// this.value = value;
// this.nameValue = name;
// }
//
// public String getNameValue()
// {
// return nameValue;
// }
//
// public void setNameValue(String nameValue)
// {
// this.nameValue = nameValue;
// }
//
// }
//
// /**
// * 类型
// */
// public enum OrderType
// {
// /**
// * 普通订单
// */
// general(0),
// /**
// * 手机APP订单
// */
// wap(1),
// /**
// * ipad订单
// */
// ipad(2),
// /**
// * 网站订单
// */
// web(3);
// private int value;
//
// public int getValue()
// {
// return value;
// }
//
// public void setValue(int value)
// {
// this.value = value;
// }
//
// private OrderType(int value)
// {
// this.value = value;
// }
// }
//
// public Long getId()
// {
// return id;
// }
//
// public void setId(Long id)
// {
// this.id = id;
// }
//
// public Date getCreateDate()
// {
// return createDate;
// }
//
// public void setCreateDate(Date createDate)
// {
// this.createDate = createDate;
// }
//
// public Date getModifyDate()
// {
// return modifyDate;
// }
//
// public void setModifyDate(Date modifyDate)
// {
// this.modifyDate = modifyDate;
// }
//
// public Long getVersion()
// {
// return version;
// }
//
// public void setVersion(Long version)
// {
// this.version = version;
// }
//
// public String getAddress()
// {
// return address;
// }
//
// public void setAddress(String address)
// {
// this.address = address == null ? null : address.trim();
// }
//
// public BigDecimal getAmount()
// {
// return amount;
// }
//
// public void setAmount(BigDecimal amount)
// {
// this.amount = amount;
// }
//
// public BigDecimal getAmountPaid()
// {
// return amountPaid;
// }
//
// public void setAmountPaid(BigDecimal amountPaid)
// {
// this.amountPaid = amountPaid;
// }
//
// public String getAreaName()
// {
// return areaName;
// }
//
// public void setAreaName(String areaName)
// {
// this.areaName = areaName == null ? null : areaName.trim();
// }
//
// public Date getCompleteDate()
// {
// return completeDate;
// }
//
// public void setCompleteDate(Date completeDate)
// {
// this.completeDate = completeDate;
// }
//
// public String getConsignee()
// {
// return consignee;
// }
//
// public void setConsignee(String consignee)
// {
// this.consignee = consignee == null ? null : consignee.trim();
// }
//
// public BigDecimal getCouponDiscount()
// {
// return couponDiscount;
// }
//
// public void setCouponDiscount(BigDecimal couponDiscount)
// {
// this.couponDiscount = couponDiscount;
// }
//
// public Long getExchangePoint()
// {
// return exchangePoint;
// }
//
// public void setExchangePoint(Long exchangePoint)
// {
// this.exchangePoint = exchangePoint;
// }
//
// public Date getExpire()
// {
// return expire;
// }
//
// public void setExpire(Date expire)
// {
// this.expire = expire;
// }
//
// public BigDecimal getFee()
// {
// return fee;
// }
//
// public void setFee(BigDecimal fee)
// {
// this.fee = fee;
// }
//
// public BigDecimal getFreight()
// {
// return freight;
// }
//
// public void setFreight(BigDecimal freight)
// {
// this.freight = freight;
// }
//
// public String getInvoiceContent()
// {
// return invoiceContent;
// }
//
// public void setInvoiceContent(String invoiceContent)
// {
// this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
// }
//
// public String getInvoiceTitle()
// {
// return invoiceTitle;
// }
//
// public void setInvoiceTitle(String invoiceTitle)
// {
// this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
// }
//
// public Boolean getIsAllocatedStock()
// {
// return isAllocatedStock;
// }
//
// public void setIsAllocatedStock(Boolean isAllocatedStock)
// {
// this.isAllocatedStock = isAllocatedStock;
// }
//
// public Boolean getIsExchangePoint()
// {
// return isExchangePoint;
// }
//
// public void setIsExchangePoint(Boolean isExchangePoint)
// {
// this.isExchangePoint = isExchangePoint;
// }
//
// public Boolean getIsUseCouponCode()
// {
// return isUseCouponCode;
// }
//
// public void setIsUseCouponCode(Boolean isUseCouponCode)
// {
// this.isUseCouponCode = isUseCouponCode;
// }
//
// public Date getLockExpire()
// {
// return lockExpire;
// }
//
// public void setLockExpire(Date lockExpire)
// {
// this.lockExpire = lockExpire;
// }
//
// public String getLockKey()
// {
// return lockKey;
// }
//
// public void setLockKey(String lockKey)
// {
// this.lockKey = lockKey == null ? null : lockKey.trim();
// }
//
// public String getMemo()
// {
// return memo;
// }
//
// public void setMemo(String memo)
// {
// this.memo = memo == null ? null : memo.trim();
// }
//
// public BigDecimal getOffsetAmount()
// {
// return offsetAmount;
// }
//
// public void setOffsetAmount(BigDecimal offsetAmount)
// {
// this.offsetAmount = offsetAmount;
// }
//
// public String getPaymentMethodName()
// {
// return paymentMethodName;
// }
//
// public void setPaymentMethodName(String paymentMethodName)
// {
// this.paymentMethodName = paymentMethodName == null ? null : paymentMethodName.trim();
// }
//
// public Integer getPaymentMethodType()
// {
// return paymentMethodType;
// }
//
// public void setPaymentMethodType(Integer paymentMethodType)
// {
// this.paymentMethodType = paymentMethodType;
// }
//
// public String getPhone()
// {
// return phone;
// }
//
// public void setPhone(String phone)
// {
// this.phone = phone == null ? null : phone.trim();
// }
//
// public BigDecimal getPrice()
// {
// return price;
// }
//
// public void setPrice(BigDecimal price)
// {
// this.price = price;
// }
//
// public BigDecimal getPromotionDiscount()
// {
// return promotionDiscount;
// }
//
// public void setPromotionDiscount(BigDecimal promotionDiscount)
// {
// this.promotionDiscount = promotionDiscount;
// }
//
// public Integer getQuantity()
// {
// return quantity;
// }
//
// public void setQuantity(Integer quantity)
// {
// this.quantity = quantity;
// }
//
// public BigDecimal getRefundAmount()
// {
// return refundAmount;
// }
//
// public void setRefundAmount(BigDecimal refundAmount)
// {
// this.refundAmount = refundAmount;
// }
//
// public Integer getReturnedQuantity()
// {
// return returnedQuantity;
// }
//
// public void setReturnedQuantity(Integer returnedQuantity)
// {
// this.returnedQuantity = returnedQuantity;
// }
//
// public Long getRewardPoint()
// {
// return rewardPoint;
// }
//
// public void setRewardPoint(Long rewardPoint)
// {
// this.rewardPoint = rewardPoint;
// }
//
// public Integer getShippedQuantity()
// {
// return shippedQuantity;
// }
//
// public void setShippedQuantity(Integer shippedQuantity)
// {
// this.shippedQuantity = shippedQuantity;
// }
//
// public String getShippingMethodName()
// {
// return shippingMethodName;
// }
//
// public void setShippingMethodName(String shippingMethodName)
// {
// this.shippingMethodName = shippingMethodName == null ? null : shippingMethodName.trim();
// }
//
// public String getSn()
// {
// return sn;
// }
//
// public void setSn(String sn)
// {
// this.sn = sn == null ? null : sn.trim();
// }
//
// public Integer getStatus()
// {
// return status;
// }
//
// public void setStatus(Integer status)
// {
// this.status = status;
// }
//
// public BigDecimal getTax()
// {
// return tax;
// }
//
// public void setTax(BigDecimal tax)
// {
// this.tax = tax;
// }
//
// public Integer getType()
// {
// return type;
// }
//
// public void setType(Integer type)
// {
// this.type = type;
// }
//
// public Integer getWeight()
// {
// return weight;
// }
//
// public void setWeight(Integer weight)
// {
// this.weight = weight;
// }
//
// public String getZipCode()
// {
// return zipCode;
// }
//
// public void setZipCode(String zipCode)
// {
// this.zipCode = zipCode == null ? null : zipCode.trim();
// }
//
// public Long getAreaId()
// {
// return areaId;
// }
//
// public void setAreaId(Long areaId)
// {
// this.areaId = areaId;
// }
//
// public Long getCouponId()
// {
// return couponId;
// }
//
// public void setCouponId(Long couponId)
// {
// this.couponId = couponId;
// }
//
// public Long getMemberId()
// {
// return memberId;
// }
//
// public void setMemberId(Long memberId)
// {
// this.memberId = memberId;
// }
//
// public Long getPaymentMethodId()
// {
// return paymentMethodId;
// }
//
// public void setPaymentMethodId(Long paymentMethodId)
// {
// this.paymentMethodId = paymentMethodId;
// }
//
// public Long getShippingMethodId()
// {
// return shippingMethodId;
// }
//
// public void setShippingMethodId(Long shippingMethodId)
// {
// this.shippingMethodId = shippingMethodId;
// }
//
// public String getPromotionNames()
// {
// return promotionNames;
// }
//
// public void setPromotionNames(String promotionNames)
// {
// this.promotionNames = promotionNames == null ? null : promotionNames.trim();
// }
// }