package com.aladdin.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "shop_goods_class")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String gc_id;
	
	@Column(name="gc_name")
	private String gc_name;
	
	@Column(name="gc_parent_id")
	private String gc_parent_id;
	
	@Column(name="gc_sort")
	private String gc_sort;
	
	@Column(name="gc_show")
	private Integer gc_show;
	
	public Integer getGc_show() {
		return gc_show;
	}

	public void setGc_show(Integer gc_show) {
		this.gc_show = gc_show;
	}

	@Column(name="is_relate")
	private Integer is_relate;
	
	public String getGc_id() {
		return gc_id;
	}

	public void setGc_id(String gc_id) {
		this.gc_id = gc_id;
	}

	public String getGc_name() {
		return gc_name;
	}

	public void setGc_name(String gc_name) {
		this.gc_name = gc_name;
	}

	public String getGc_parent_id() {
		return gc_parent_id;
	}

	public void setGc_parent_id(String gc_parent_id) {
		this.gc_parent_id = gc_parent_id;
	}

	public String getGc_sort() {
		return gc_sort;
	}

	public void setGc_sort(String gc_sort) {
		this.gc_sort = gc_sort;
	}

	public Integer getIs_relate() {
		return is_relate;
	}

	public void setIs_relate(Integer is_relate) {
		this.is_relate = is_relate;
	}
	
	
	
}


