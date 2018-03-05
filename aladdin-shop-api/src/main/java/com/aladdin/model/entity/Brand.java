package com.aladdin.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "shop_brand")
public class Brand implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String brand_id;
	
	@Column(name="brand_name")
	private String brand_name;
	
	//是否删除0:未删除;1:已删除
	@Column(name="is_del")
	private Integer is_del;

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

}
