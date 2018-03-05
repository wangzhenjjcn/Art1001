package com.aladdin.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单商品(订单项)
 * @author Liuk  
 */
@Table(name="shop_order_goods")
public class OrderGoods implements Serializable{

    private static final long serialVersionUID = -2895477428850499265L;

    /**
     * 订单商品表索引id
     */
    @Id
    @Column(name="rec_id")
    private String recId;
    
    /**
     * 订单id
     */
    @Column(name="order_id")
    private String orderId;
    
    /**
     * 商品id
     */
    @Column(name="goods_id")
    private String goodsId;
    
    /**
     * 商品名称
     */
    @Column(name="goods_name")
    private String goodsName;
    
    /**
     * 规格id
     */
    @Column(name="spec_id")
    private String specId;
    
    /**
     * 规格描述
     */
    @Column(name="spec_info")
    private String specInfo;
    
    /**
     * 商品价格
     */
    @Column(name="goods_price")
    private BigDecimal goodsPrice;
    
    /**
     * 商品数量
     */
    @Column(name="goods_num")
    private Integer goodsNum;
    
    /**
     * 商品图片
     */
    @Column(name="goods_image")
    private String goodsImage;
    
    /**
     * 退货数量
     */
    @Column(name="goods_returnnum")
    private Integer goodsReturnNum;
    
    /**
     * 退款金额
     */
    @Column(name="refund_amount")
    private BigDecimal refundAmount;
    
    /**
     * 换货数量
     */
    @Column(name="goods_barternum")
    private Integer goodsBarterNum;
    
    
    /**
     * 店铺ID
     */
    @Column(name="stores_id")
    private String storeId;
    
    /**
     * 用户ID
     */
    @Column(name="buyer_id")
    private String buyerId;
    
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
	 * 商品实际成交价
	 */
    @Column(name="goods_pay_price")
	private BigDecimal goodsPayPrice;
	
	/**
	 * 订单项余额支付金额
	 */
    @Column(name="goods_pre_amount")
	private BigDecimal goodsPreAmount;
	
	/**
	 * 佣金比例
	 */
    @Column(name="commis_rate")
	private Float commisRate;
	
	/**
	 * 商品最底级分类ID
	 */
    @Column(name="gc_id")
	private String gcId;

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getSpecInfo() {
		return specInfo;
	}

	public void setSpecInfo(String specInfo) {
		this.specInfo = specInfo;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public Integer getGoodsReturnNum() {
		return goodsReturnNum;
	}

	public void setGoodsReturnNum(Integer goodsReturnNum) {
		this.goodsReturnNum = goodsReturnNum;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getGoodsBarterNum() {
		return goodsBarterNum;
	}

	public void setGoodsBarterNum(Integer goodsBarterNum) {
		this.goodsBarterNum = goodsBarterNum;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
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

	public BigDecimal getGoodsPayPrice() {
		return goodsPayPrice;
	}

	public void setGoodsPayPrice(BigDecimal goodsPayPrice) {
		this.goodsPayPrice = goodsPayPrice;
	}

	public BigDecimal getGoodsPreAmount() {
		return goodsPreAmount;
	}

	public void setGoodsPreAmount(BigDecimal goodsPreAmount) {
		this.goodsPreAmount = goodsPreAmount;
	}

	public Float getCommisRate() {
		return commisRate;
	}

	public void setCommisRate(Float commisRate) {
		this.commisRate = commisRate;
	}

	public String getGcId() {
		return gcId;
	}

	public void setGcId(String gcId) {
		this.gcId = gcId;
	}

}