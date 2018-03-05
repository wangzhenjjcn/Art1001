package com.aladdin.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "shop_goods")
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String goods_id;

	@Column(name = "goods_name")
	private String goods_name;

	@Column(name = "gc_id")
	private String gc_id;

	@Column(name = "brand_id")
	private String brand_id;
	@Transient
	private String brandName;

	@Column(name = "goods_image")
	private String goods_image;

	@Column(name = "goods_show")
	private Integer goods_show;

	@Column(name = "is_del")
	private Integer is_del;
	
	@Column(name="goods_state")
	private Integer goods_state;

	public Integer getGoods_state() {
		return goods_state;
	}

	public void setGoods_state(Integer goods_state) {
		this.goods_state = goods_state;
	}

	@Column(name = "store_id")
	private String storeId;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "spec_name")
	private String specName;

	@Column(name = "goods_spec")
	private String goodsSpec;

	/**
	 * 表示当前商品是否已经建立过单品模型(0:没有，1：有)
	 */
	@Transient
	private Byte is_exist = 0;
	
	@Transient
	private List<Sku> skus;

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Byte getIs_exist() {
		return is_exist;
	}

	public void setIs_exist(Byte is_exist) {
		this.is_exist = is_exist;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGc_id() {
		return gc_id;
	}

	public void setGc_id(String gc_id) {
		this.gc_id = gc_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public Integer getGoods_show() {
		return goods_show;
	}

	public void setGoods_show(Integer goods_show) {
		this.goods_show = goods_show;
	}

}
