package com.aladdin.order.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 订单支付表,(父订单),多个订单一块支付 ShopOrderPay entity. @author MyEclipse Persistence Tools
 */
@Table(name = "shop_order_pay")
public class OrderPay implements Serializable {

	@Column(name = "pay_id")
	private String payId;
	// 支付单号
	@Column(name = "pay_sn")
	private String paySn;
	// 买家ID
	@Column(name = "buyer_id")
	private String buyerId;
	// 0默认未支付1已支付(只有第三方支付接口通知到时才会更改此状态)
	@Column(name = "api_pay_state")
	private String apiPayState;

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

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getApiPayState() {
		return apiPayState;
	}

	public void setApiPayState(String apiPayState) {
		this.apiPayState = apiPayState;
	}

}