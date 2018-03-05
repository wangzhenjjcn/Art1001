package com.aladdin.order.entity;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 订单地址信息表
 * @author liukai
 */
@Table(name="shop_order_address")
public class OrderAddress implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址id
	 */
	@Column(name="address_id")
	private String addressId;
	
	/**
	 * 会员id
	 */
	@Column(name="member_id")
	private String memberId;
	
	/**
	 * 会员姓名
	 */
	@Column(name="true_name")
	private String trueName;
	
	/**
	 * 地区id
	 */
	@Column(name="area_id")
	private String areaId;
	
	/**
	 * 市级id
	 */
	@Column(name="city_id")
	private String cityId;
	
	/**
	 * 地区内容
	 */
	@Column(name="area_info")
	private String areaInfo;
	
	/**
	 * 地址
	 */
	@Column(name="address")
	private String address;
	
	/**
	 * 座机电话
	 */
	@Column(name="tel_phone")
	private String telPhone;
	
	/**
	 * 手机电话
	 */
	@Column(name="mob_phone")
	private String mobPhone;
	
	/**
	 * 默认收货地址
	 */
	@Column(name="is_default")
	private String isDefault;
	
	/**
	 * 省级id
	 */
	@Column(name="province_id")
	private String provinceId;
	
	/**
	 * 邮编
	 */
	@Column(name="zip_code")
	private Integer zipCode;

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMobPhone() {
		return mobPhone;
	}

	public void setMobPhone(String mobPhone) {
		this.mobPhone = mobPhone;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

}
