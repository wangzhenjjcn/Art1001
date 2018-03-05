package com.aladdin.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Table(name = "shop_goods_spec")
public class Sku implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "goods_spec_id")
	private String goodsSpecId;
	
	@Column(name = "goods_id")
	private String goodsId;
	
	@Column(name = "spec_goods_spec")
	private String specGoodsSpec;
	
	@Column(name = "spec_goods_price")
	private BigDecimal specGoodsPrice;
	
	//由sku对应规格组合成的skuName
	@Transient
	private String skuName; 
	
	@Transient
	private Goods good; 
	
	/**
	 * 表示当前商品是否已经建立过单品模型(0:没有，1：有)
	 */
	@Transient
	private Byte is_exist=0;
	
	
	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public BigDecimal getSpecGoodsPrice() {
		return specGoodsPrice;
	}

	public void setSpecGoodsPrice(BigDecimal specGoodsPrice) {
		this.specGoodsPrice = specGoodsPrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public Byte getIs_exist() {
		return is_exist;
	}

	public void setIs_exist(Byte is_exist) {
		this.is_exist = is_exist;
	}

	public String getSpecGoodsSpec() {
		return specGoodsSpec;
	}

	public void setSpecGoodsSpec(String specGoodsSpec) {
		if(specGoodsSpec!=null&&!"".equals(specGoodsSpec.trim())){
			Map<String,String> map =  new Gson().fromJson(specGoodsSpec, 
											new TypeToken<TreeMap<String, String>>() {}.getType());
			 if(map!=null&&map.values()!=null&&!map.values().isEmpty()){
				 Iterator<String> it = map.values().iterator();
				 this.skuName = "";
				 while(it.hasNext()){
					 this.skuName+=it.next().trim()+" ";
				 }
				 this.skuName = this.skuName.trim();
			 }
		}
		this.specGoodsSpec = specGoodsSpec;
	}

	public String getGoodsSpecId() {
		return goodsSpecId;
	}

	public void setGoodsSpecId(String goodsSpecId) {
		this.goodsSpecId = goodsSpecId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}
