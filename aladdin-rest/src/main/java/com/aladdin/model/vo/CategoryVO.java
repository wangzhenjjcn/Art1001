package com.aladdin.model.vo;

import java.io.Serializable;
import java.util.List;

public class CategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gc_id;
	
	private String gc_name;
	
	private String gc_parent_id;
	
	private String gc_sort;
	
	private Integer gc_show;
	
	public Integer getGc_show() {
		return gc_show;
	}

	public void setGc_show(Integer gc_show) {
		this.gc_show = gc_show;
	}
	
	private Integer is_relate;
	
	private List<CategoryVO> categoryVOs;
	
	public List<CategoryVO> getCategoryVOs() {
		return categoryVOs;
	}

	public void setCategoryVOs(List<CategoryVO> categoryVOs) {
		this.categoryVOs = categoryVOs;
	}


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


