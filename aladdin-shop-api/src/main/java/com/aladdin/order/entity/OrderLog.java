package com.aladdin.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "shop_order_log")
public class OrderLog implements Serializable {

	/**
	 * 订单处理历史索引id
	 */
	@Id
	@Column(name="log_id")
	private String logId;

	/**
	 * 订单id
	 */
	@Column(name="order_id")
	private String orderId;

	/**
	 * 订单状态信息
	 */
	@Column(name="order_state")
	private String orderState;

	/**
	 * 下一步订单状态信息
	 */
	@Column(name="change_state")
	private String changeState;

	/**
	 * 订单状态描述
	 */
	@Column(name="state_info")
	private String stateInfo;

	@Column(name="create_time")
	private long createTime;

	/**
	 * 操作人
	 */
	private String operator;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}