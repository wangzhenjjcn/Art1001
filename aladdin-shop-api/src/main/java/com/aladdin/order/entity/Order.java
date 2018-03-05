package com.aladdin.order.entity;

import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="shop_order")
public class Order implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	/**
	 * 订单索引id
	 */
	@Id
	@Column(name="order_id")
	private String orderId;
	
	/**
	 * 订单编号，商城内部使用
	 */
	@Column(name="order_sn")
	private String orderSn;
	
	/**
	 * 卖家店铺id
	 */
	@Column(name="store_id")
	private String storeId;
	
	/**
	 * 卖家店铺名称
	 */
	@Column(name="store_name")
	private String storeName;
	
	/**
	 * 买家id
	 */
	@Column(name="buyer_id")
	private String buyerId;
	
	/**
	 * 买家姓名
	 */
	@Column(name="buyer_name")
	private String buyerName;
	
	/**
	 * 买家电子邮箱
	 */
	@Column(name="buyer_email")
	private String buyerEmail;

	@Column(name="create_time")
	private long createTime;
	
	/**
	 * 订单类型 0.普通 1.团购
	 */
	@Column(name="order_type")
	private Integer orderType;
	
	/**
	 * 支付方式id
	 */
	@Column(name="payment_id")
	private String paymentId;
	
	/**
	 * 支付方式名称
	 */
	@Column(name="payment_name")
	private String paymentName;
	
	/**
	 * 支付方式名称代码
	 */
	@Column(name="payment_code")
	private String paymentCode;
	
	/**
	 * 支付分支
	 */
	@Column(name="payment_branch")
	private String paymentBranch;
	
	/**
	 * 支付类型:1是即时到帐,2是担保交易
	 */
	@Column(name="payment_direct")
	private String paymentDirect;
	
	/**
	 * 付款状态:0:未付款;1:已付款
	 */
	@Column(name="payment_state")
	private Integer paymentState;
	
	/**
	 * 支付(付款)时间
	 */
	@Column(name="payment_time")
	private Long paymentTime;
	
	/**
	 * 订单编号，外部支付时使用，有些外部支付系统要求特定的订单编号
	 */
	@Column(name="out_sn")
	private String outSn;
	
	/**
	 * 交易流水号
	 */
	@Column(name="trade_sn")
	private String tradeSn;
	
	/**
	 * 外部交易平台单独使用的标识字符串
	 */
	@Column(name="out_payment_code")
	private String outPaymentCode;
	
	/**
	 * 支付留言
	 */
	@Column(name="pay_message")
	private String payMessage;
	
	/**
	 * 配送时间
	 */
	@Column(name="shipping_time")
	private Long shippingTime;
	
	
	/**
	 * 配送公司ID
	 */
	@Column(name="shipping_express_id")
	private String shippingExpressId;
	
	/**
	 * 配送公司编号
	 */
	private String shippingExpressCode;
	
	/**
	 * 物流单号
	 */
	@Column(name="shipping_code")
	private String shippingCode;
	
	/**
	 * 订单完成时间
	 */
	@Column(name="finnshed_time")
	private Long finnshedTime;
	
	
	/**
	 * 发票信息
	 */
	private String invoice;
	
	/**
	 * 商品总价格
	 */
	@Column(name="goods_amount")
	private BigDecimal goodsAmount;
	
	/**
	 * 优惠总金额
	 */
	private BigDecimal discount;
	
	/**
	 * 订单应付金额
	 */
	@Column(name="order_amount")
	private BigDecimal orderAmount;
	
	/**
	 * 订单总价格
	 */
	@Column(name="order_total_price")
	private BigDecimal orderTotalPrice;
	
	/**
	 * 促销优惠金额
	 */
	@Column(name="promo_price")
	private BigDecimal promoPrice;
	
	/**
	 * 余额支付金额
	 */
	@Column(name="predeposit_amount")
	private BigDecimal predepositAmount;
	
	/**
	 * 运费价格
	 */
	@Column(name="shipping_fee")
	private BigDecimal shippingFee;
	
	/**
	 * 配送方式
	 */
	@Column(name="shipping_name")
	private String shippingName;
	
	/**
	 * 评价状态 0为评价，1已评价
	 */
	@Column(name="evaluation_status")
	private Integer evaluationStatus;
	
	/**
	 * 评价时间
	 */
	
	@Column(name="evaluation_time")
	private Long evaluationTime;
	
	/**
	 * 卖家是否已评价买家
	 */
	@Column(name="evalseller_status")
	private Integer evalsellerStatus;
	
	/**
	 * 卖家评价买家的时间
	 */
	@Column(name="evalseller_time")
	private Long evalsellerTime;
	
	/**
	 * 订单留言
	 */
	@Column(name="order_message")
	private String orderMessage;
	
	/**
	 * 订单状态：0:已取消;10:待付款;20:待发货;30:待收货;40:交易完成;50:已提交;60:已确认;
	 */
	@Column(name="order_state")
	private Integer orderState;
	
	/**
	 * 订单赠送积分
	 */
	@Column(name="order_pointscount")
	private Integer orderPointscount;
	
	/**
	 * 代金券id
	 */
	@Column(name="voucher_id")
	private String voucherId;
	
	/**
	 * 代金券面额
	 */
	@Column(name="voucher_price")
	private BigDecimal voucherPrice;
	
	/**
	 * 代金券编码
	 */
	@Column(name="voucher_code")
	private String voucherCode;
	
	/**
	 * 优惠券id
	 */
	@Column(name="coupon_id")
	private String couponId;
	
	/**
	 * 优惠券金额
	 */
	@Column(name="coupon_price")
	private BigDecimal couponPrice;
	
	/**
	 * 退款状态:0是无退款,1是部分退款,2是全部退款
	 */
	@Column(name="refund_state")
	private Integer refundState;
	
	/**
	 * 退货状态:0是无退货,1是部分退货,2是全部退货
	 */
	@Column(name="return_state")
	private Integer returnState;
	
	/**
	 * 换货状态:70是无换货,80是部分换货,90是全部换货
	 */
	@Column(name="barter_state")
	private Integer barterState;
	
	/**
	 * 退款金额
	 */
	@Column(name="refund_amount")
	private BigDecimal refundAmount;
	
	/**
	 * 退货数量
	 */
	@Column(name="return_num")
	private Integer returnNum;
	
	/**
	 * 换货数量
	 */
	@Column(name="barter_num")
	private Integer barterNum;
	
	/**
	 * 团购编号(非团购订单为0)
	 */
	@Column(name="group_id")
	private String groupId;
	
	/**
	 * 团购数量
	 */
	@Column(name="group_count")
	private Integer groupCount;
	
	/**
	 *  限时折扣编号
	 */
	@Column(name="xianshi_id")
	private String xianshiId;
	
	/**
	 * 限时折扣说明
	 */
	@Column(name="xianshi_explain")
	private String xianshiExplain;
	
	/**
	 * 满就送编号
	 */
	@Column(name="mansong_id")
	private String mansongId;
	
	/**
	 * 满就送说明
	 */
	@Column(name="mansong_explain")
	private String mansongExplain;
	
	/**
	 * 搭配套餐id
	 */
	@Column(name="bundling_id")
	private String bundlingId;
	
	/**
	 * 搭配套餐说明
	 */
	@Column(name="bundling_explain")
	private String bundlingExplain;
	
	/**
	 * 1PC2手机端
	 */
	@Column(name="order_from")
	private String orderFrom;
	
	/**
	 * 订单取消原因
	 */
	@Column(name="cancel_cause")
	private String cancelCause;
	
	/**
	 * 发货备注
	 */
	@Column(name="deliver_explain")
	private String deliverExplain;
	
	/**
	 * 发货地址ID
	 */
	@Column(name="daddress_id")
	private String daddressId;
	
    /**
     * 收货地址id
     */
	@Column(name="address_id")
    private String addressId;
    
    /**
     * 订单支付表id
     */
	@Column(name="pay_id")
    private String payId;
    
    /**
     * 订单支付表编号
     */
	@Column(name="pay_sn")
    private String paySn;
    
    /**
     * 结算状态:0未结算,1已结算
     */
	@Column(name="balance_state")
    private Integer balanceState;
    
    /**
     * 结算时间
     */
	@Column(name="balance_time")
    private Long balanceTime;
    
    /**
     * 锁定状态:0是正常,大于0是锁定,默认是0
     */
	@Column(name="lock_state")
    private Integer lockState;


	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getOrderSn() {
		return orderSn;
	}



	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}



	public String getBuyerId() {
		return buyerId;
	}



	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}



	public String getBuyerName() {
		return buyerName;
	}



	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}



	public String getBuyerEmail() {
		return buyerEmail;
	}



	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}



	public Integer getOrderType() {
		return orderType;
	}



	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}



	public String getPaymentId() {
		return paymentId;
	}



	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}



	public String getPaymentName() {
		return paymentName;
	}



	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}



	public String getPaymentCode() {
		return paymentCode;
	}



	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}



	public String getPaymentBranch() {
		return paymentBranch;
	}



	public void setPaymentBranch(String paymentBranch) {
		this.paymentBranch = paymentBranch;
	}



	public String getPaymentDirect() {
		return paymentDirect;
	}



	public void setPaymentDirect(String paymentDirect) {
		this.paymentDirect = paymentDirect;
	}



	public Integer getPaymentState() {
		return paymentState;
	}



	public void setPaymentState(Integer paymentState) {
		this.paymentState = paymentState;
	}



	public Long getPaymentTime() {
		return paymentTime;
	}



	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}






	public String getOutSn() {
		return outSn;
	}



	public void setOutSn(String outSn) {
		this.outSn = outSn;
	}



	public String getTradeSn() {
		return tradeSn;
	}



	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}



	public String getOutPaymentCode() {
		return outPaymentCode;
	}



	public void setOutPaymentCode(String outPaymentCode) {
		this.outPaymentCode = outPaymentCode;
	}



	public String getPayMessage() {
		return payMessage;
	}



	public void setPayMessage(String payMessage) {
		this.payMessage = payMessage;
	}



	public Long getShippingTime() {
		return shippingTime;
	}



	public void setShippingTime(Long shippingTime) {
		this.shippingTime = shippingTime;
	}




	public String getShippingExpressId() {
		return shippingExpressId;
	}



	public void setShippingExpressId(String shippingExpressId) {
		this.shippingExpressId = shippingExpressId;
	}



	public String getShippingExpressCode() {
		return shippingExpressCode;
	}



	public void setShippingExpressCode(String shippingExpressCode) {
		this.shippingExpressCode = shippingExpressCode;
	}



	public String getShippingCode() {
		return shippingCode;
	}



	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}



	public Long getFinnshedTime() {
		return finnshedTime;
	}



	public void setFinnshedTime(Long finnshedTime) {
		this.finnshedTime = finnshedTime;
	}



	public String getInvoice() {
		return invoice;
	}



	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}



	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}



	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}



	public BigDecimal getDiscount() {
		return discount;
	}



	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}



	public BigDecimal getOrderAmount() {
		return orderAmount;
	}



	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}



	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}



	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}



	public BigDecimal getPromoPrice() {
		return promoPrice;
	}



	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}



	public BigDecimal getPredepositAmount() {
		return predepositAmount;
	}



	public void setPredepositAmount(BigDecimal predepositAmount) {
		this.predepositAmount = predepositAmount;
	}



	public BigDecimal getShippingFee() {
		return shippingFee;
	}



	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}



	public String getShippingName() {
		return shippingName;
	}



	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}



	public Integer getEvaluationStatus() {
		return evaluationStatus;
	}



	public void setEvaluationStatus(Integer evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}



	public Long getEvaluationTime() {
		return evaluationTime;
	}



	public void setEvaluationTime(Long evaluationTime) {
		this.evaluationTime = evaluationTime;
	}



	public Integer getEvalsellerStatus() {
		return evalsellerStatus;
	}



	public void setEvalsellerStatus(Integer evalsellerStatus) {
		this.evalsellerStatus = evalsellerStatus;
	}



	public Long getEvalsellerTime() {
		return evalsellerTime;
	}



	public void setEvalsellerTime(Long evalsellerTime) {
		this.evalsellerTime = evalsellerTime;
	}



	public String getOrderMessage() {
		return orderMessage;
	}



	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}



	public Integer getOrderState() {
		return orderState;
	}



	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}



	public Integer getOrderPointscount() {
		return orderPointscount;
	}



	public void setOrderPointscount(Integer orderPointscount) {
		this.orderPointscount = orderPointscount;
	}



	public String getVoucherId() {
		return voucherId;
	}



	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}



	public BigDecimal getVoucherPrice() {
		return voucherPrice;
	}



	public void setVoucherPrice(BigDecimal voucherPrice) {
		this.voucherPrice = voucherPrice;
	}



	public String getVoucherCode() {
		return voucherCode;
	}



	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}



	public String getCouponId() {
		return couponId;
	}



	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}



	public BigDecimal getCouponPrice() {
		return couponPrice;
	}



	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}



	public Integer getRefundState() {
		return refundState;
	}



	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}



	public Integer getReturnState() {
		return returnState;
	}



	public void setReturnState(Integer returnState) {
		this.returnState = returnState;
	}



	public Integer getBarterState() {
		return barterState;
	}



	public void setBarterState(Integer barterState) {
		this.barterState = barterState;
	}



	public BigDecimal getRefundAmount() {
		return refundAmount;
	}



	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}



	public Integer getReturnNum() {
		return returnNum;
	}



	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}



	public Integer getBarterNum() {
		return barterNum;
	}



	public void setBarterNum(Integer barterNum) {
		this.barterNum = barterNum;
	}



	public String getGroupId() {
		return groupId;
	}



	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



	public Integer getGroupCount() {
		return groupCount;
	}



	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}



	public String getXianshiId() {
		return xianshiId;
	}



	public void setXianshiId(String xianshiId) {
		this.xianshiId = xianshiId;
	}



	public String getXianshiExplain() {
		return xianshiExplain;
	}



	public void setXianshiExplain(String xianshiExplain) {
		this.xianshiExplain = xianshiExplain;
	}



	public String getMansongId() {
		return mansongId;
	}



	public void setMansongId(String mansongId) {
		this.mansongId = mansongId;
	}



	public String getMansongExplain() {
		return mansongExplain;
	}



	public void setMansongExplain(String mansongExplain) {
		this.mansongExplain = mansongExplain;
	}



	public String getBundlingId() {
		return bundlingId;
	}



	public void setBundlingId(String bundlingId) {
		this.bundlingId = bundlingId;
	}



	public String getBundlingExplain() {
		return bundlingExplain;
	}



	public void setBundlingExplain(String bundlingExplain) {
		this.bundlingExplain = bundlingExplain;
	}



	public String getOrderFrom() {
		return orderFrom;
	}



	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}



	public String getCancelCause() {
		return cancelCause;
	}



	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}



	public String getDeliverExplain() {
		return deliverExplain;
	}



	public void setDeliverExplain(String deliverExplain) {
		this.deliverExplain = deliverExplain;
	}



	public String getDaddressId() {
		return daddressId;
	}



	public void setDaddressId(String daddressId) {
		this.daddressId = daddressId;
	}



	public String getAddressId() {
		return addressId;
	}



	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}



	public String getPayId() {
		return payId;
	}



	public void setPayId(String payId) {
		this.payId = payId;
	}



	public String getPaySn() {
		return paySn;
	}



	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}



	public Integer getBalanceState() {
		return balanceState;
	}



	public void setBalanceState(Integer balanceState) {
		this.balanceState = balanceState;
	}



	public Long getBalanceTime() {
		return balanceTime;
	}



	public void setBalanceTime(Long balanceTime) {
		this.balanceTime = balanceTime;
	}



	public long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}



	public Integer getLockState() {
		return lockState;
	}



	public void setLockState(Integer lockState) {
		this.lockState = lockState;
	}
 
}